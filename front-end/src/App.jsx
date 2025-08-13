import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import Navbar from './components/Navbar';
import { checkSession } from './api/auth';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const verifySession = async () => {
            try {
                const hasSession = await checkSession();
                setIsAuthenticated(hasSession);
            } catch (error) {
                console.error('Session check failed:', error.message);
                setIsAuthenticated(false);
            } finally {
                setLoading(false);
            }
        };

        verifySession();
    }, []);

    if (loading) {
        return (
            <div className="flex items-center justify-center min-h-screen">
                <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
            </div>
        );
    }

    return (
        <Router>
            <div className="min-h-screen bg-gray-100">
                <Navbar isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
                <div className="container mx-auto px-4 py-8">
                    <Routes>
                        <Route
                            path="/login"
                            element={!isAuthenticated ? <Login setIsAuthenticated={setIsAuthenticated} /> : <Navigate to="/" />}
                        />
                        <Route
                            path="/register"
                            element={!isAuthenticated ? <Register /> : <Navigate to="/" />}
                        />
                        <Route
                            path="/"
                            element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />}
                        />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;