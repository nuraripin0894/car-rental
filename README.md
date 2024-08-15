# Car Rental API

This document provides an overview of the endpoints available in the Car Rental API.

## Authentication

### Register
- **POST** `/api/auth/register`
- **Description**: Registers a new user.
- **Request Body**: `AuthResponDTO`
- **Response**: `AuthResponDTO` with HTTP status 201 Created.

### Login
- **POST** `/api/auth/login`
- **Description**: Logs in a user and returns an authentication token.
- **Request Body**: `AuthResponDTO`
- **Response**: `AuthResponDTO` with HTTP status 200 OK.

### Refresh Token
- **POST** `/api/auth/refresh`
- **Description**: Refreshes the authentication token.
- **Request Body**: `AuthResponDTO`
- **Response**: `AuthResponDTO` with HTTP status 200 OK.

## Brands

### Create Brand
- **POST** `/brands`
- **Description**: Creates a new brand.
- **Request Body**: `Brand`
- **Response**: `Brand` with HTTP status 201 Created.

### Get All Brands
- **GET** `/brands`
- **Description**: Retrieves all brands with pagination.
- **Request Parameters**: `page`, `size`
- **Response**: `PageResponseWrapper<Brand>` with HTTP status 200 OK.

### Get Brand by ID
- **GET** `/brands/{id}`
- **Description**: Retrieves a brand by its ID.
- **Path Variable**: `id`
- **Response**: `Brand` with HTTP status 200 OK.

### Update Brand
- **PUT** `/brands/{id}`
- **Description**: Updates a brand by its ID.
- **Path Variable**: `id`
- **Request Body**: `Brand`
- **Response**: `Brand` with HTTP status 200 OK.

### Delete Brand
- **DELETE** `/brands/{id}`
- **Description**: Deletes a brand by its ID.
- **Path Variable**: `id`
- **Response**: HTTP status 200 OK.

## Cars

### Create Car
- **POST** `/cars`
- **Description**: Creates a new car.
- **Request Body**: `CarDTO`
- **Response**: `Car` with HTTP status 201 Created.

### Get All Cars
- **GET** `/cars`
- **Description**: Retrieves all cars with pagination.
- **Request Parameters**: `page`, `size`
- **Response**: `PageResponseWrapper<Car>` with HTTP status 200 OK.

### Get Car by ID
- **GET** `/cars/{id}`
- **Description**: Retrieves a car by its ID.
- **Path Variable**: `id`
- **Response**: `Car` with HTTP status 200 OK.

### Update Car
- **PUT** `/cars/{id}`
- **Description**: Updates a car by its ID.
- **Path Variable**: `id`
- **Request Body**: `CarDTO`
- **Response**: `Car` with HTTP status 200 OK.

### Delete Car
- **DELETE** `/cars/{id}`
- **Description**: Deletes a car by its ID.
- **Path Variable**: `id`
- **Response**: HTTP status 200 OK.

## Rents

### Create Rent
- **POST** `/rents`
- **Description**: Creates a new rent record.
- **Request Body**: `RentDTO`
- **Response**: `Rent` with HTTP status 201 Created.

### Get All Rents
- **GET** `/rents`
- **Description**: Retrieves all rent records with pagination.
- **Request Parameters**: `page`, `size`
- **Response**: `PageResponseWrapper<Rent>` with HTTP status 200 OK.

### Get Rent by ID
- **GET** `/rents/{id}`
- **Description**: Retrieves a rent record by its ID.
- **Path Variable**: `id`
- **Response**: `Rent` with HTTP status 200 OK.

### Update Rent
- **PUT** `/rents/{id}`
- **Description**: Updates a rent record by its ID.
- **Path Variable**: `id`
- **Response**: `Rent` with HTTP status 200 OK.

### Delete Rent
- **DELETE** `/rents/{id}`
- **Description**: Deletes a rent record by its ID.
- **Path Variable**: `id`
- **Response**: HTTP status 200 OK.

## Users

### Get All Users
- **GET** `/users`
- **Description**: Retrieves all users with pagination.
- **Request Parameters**: `page`, `size`
- **Response**: `PageResponseWrapper<UserEntity>` with HTTP status 200 OK.

### Get User by ID
- **GET** `/users/{id}`
- **Description**: Retrieves a user by its ID.
- **Path Variable**: `id`
- **Response**: `UserEntity` with HTTP status 200 OK.

### Update User
- **PUT** `/users`
- **Description**: Updates a user's information.
- **Request Body**: `UserEntity`
- **Response**: `UserEntity` with HTTP status 200 OK.

### Delete User by ID
- **DELETE** `/users/{id}`
- **Description**: Deletes a user by its ID.
- **Path Variable**: `id`
- **Response**: HTTP status 200 OK.

### Delete User (Authenticated)
- **DELETE** `/users`
- **Description**: Deletes the currently authenticated user.
- **Response**: HTTP status 200 OK.

### Top Up Balance
- **POST** `/users/topup`
- **Description**: Top up the balance for the currently authenticated user.
- **Request Body**: `UserEntity`
- **Response**: `UserEntity` with HTTP status 200 OK.
