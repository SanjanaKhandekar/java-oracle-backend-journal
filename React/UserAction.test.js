// Day 26: Testing text inputs and simulation events on target form elements
// Topic: Jest Simulated Events & Async UI Mocking

import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import React from 'react';
import LoginForm from './LoginForm';

describe('Login Flow Callback Validation', () => {
    test('submits input values to execution handler on action click', async () => {
        // Arrange: Create a mock function to spy on form submissions
        const mockSubmit = jest.fn();
        render(<LoginForm onSubmit={mockSubmit} />);

        // Act: Locate text inputs and simulate a user typing a username
        fireEvent.change(screen.getByPlaceholderText(/Enter Username/i), { 
            target: { value: 'rahul_dev' } 
        });
        
        // Simulate a user clicking the submit button
        fireEvent.click(screen.getByRole('button', { name: /Submit/i }));

        // Assert: Wait for asynchronous code and verify the callback was triggered correctly
        await waitFor(() => {
            expect(mockSubmit).toHaveBeenCalledTimes(1);
            expect(mockSubmit).toHaveBeenCalledWith({ username: 'rahul_dev' });
        });
    });
});

// Benefit: Blocks submission bugs. Automating form simulation tests ensures that 
// interactive elements continue to bundle payloads correctly even after large-scale 
// UI changes, protecting your application's input processing logic.
