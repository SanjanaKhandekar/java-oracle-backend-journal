// Day 8: Controlled Forms in React for sending POST payloads to Spring Boot
// Topic: State Management, Form Validations, and Event Handlers

import React, { useState } from 'react';

export default function AddProductForm() {
    // Single state object managing multiple form input values
    const [product, setProduct] = useState({ name: '', price: '' });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct({ ...product, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        // Basic frontend input validation sanity check
        if (!product.name || !product.price) {
            alert('Please fill out all product parameters before submitting.');
            return;
        }

        // Simulating the transactional payload post to the Spring Boot REST API layer
        fetch('http://localhost:8080/api/v1/products', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        })
        .then(res => {
            if (!res.ok) throw new Error('Failed to save record to the database.');
            return res.json();
        })
        .then(data => {
            alert('Product successfully committed to database with ID: ' + data.id);
            setProduct({ name: '', price: '' }); // Reset form states cleanly
        })
        .catch(err => alert('Network error processing transaction: ' + err.message));
    };

    return (
        <div className="form-container">
            <h2>Add New Enterprise Product</h2>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text" 
                    name="name" 
                    placeholder="Product Name" 
                    value={product.name} 
                    onChange={handleChange} 
                />
                <input 
                    type="number" 
                    name="price" 
                    placeholder="Product Price" 
                    value={product.price} 
                    onChange={handleChange} 
                />
                <button type="submit">Submit to Backend</button>
            </form>
        </div>
    );
}
