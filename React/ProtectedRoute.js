// Day 14: Restricting dashboard view access using a Custom Protected Route Component
// Topic: React Router Authentication Guards & Client-Side Security

import React from 'react';
import { Navigate } from 'react-router-dom';

/**
 * A wrapper component that checks for user authentication tokens 
 * before allowing access to private layout views.
 */
export default function ProtectedRoute({ children }) {
    // Simulate reading a secure authorization token from browser memory
    const isAuthenticated = localStorage.getItem('authToken') !== null;

    if (!isAuthenticated) {
        // Force unauthenticated session traffic back to the login window
        // The 'replace' property clears the history stack to prevent back-button bypasses
        return <Navigate to="/login" replace />;
    }

    // Allow entry to the dashboard or private components if authenticated
    return children;
}

// Benefit: Centralizes route protection. Instead of checking for session tokens 
// inside every single private screen file, wrapping your routers in this guard 
// defends your application layout systematically.
