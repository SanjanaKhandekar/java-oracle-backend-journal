// Day 5: Fetching data from a Spring Boot Backend API cleanly using React Hooks
// Topic: Frontend-Backend Integration & State Management

import React, { useState, useEffect } from 'react';

export default function EmployeeList() {
    const [employees, setEmployees] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/v1/employees')
            .then(response => {
                if (!response.ok) throw new Error('Network response was not stable');
                return response.json();
            })
            .then(data => setEmployees(data))
            .catch(err => setError(err.message));
    }, []);

    if (error) return <div className="error">Error loading records: {error}</div>;

    return (
        <ul>
            {employees.map(emp => (
                <li key={emp.id}>{emp.name} - {emp.department}</li>
            ))}
        </ul>
    );
}
