const API_URL = process.env.REACT_APP_API_URL;

const handleResponse = async (response) => {
    if (!response.ok) {
        const error = await response.text();
        throw new Error(error || 'Request failed');
    }
    return response.json();
};

export const register = async (userData) => {
    const response = await fetch(`${API_URL}/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
        credentials: 'include',
    });
    return handleResponse(response);
};

export const login = async (userData) => {
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
        credentials: 'include',
    });
    return handleResponse(response);
};

export const checkSession = async () => {
    const response = await fetch(`${API_URL}/check-session`, {
        method: 'GET',
        credentials: 'include',
    });
    return handleResponse(response);
};

export const logout = async () => {
    const response = await fetch(`${API_URL}/logout`, {
        method: 'POST',
        credentials: 'include',
    });
    return handleResponse(response);
};