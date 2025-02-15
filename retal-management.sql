-- Database: rental_management

-- DROP DATABASE IF EXISTS rental_management;

CREATE DATABASE rental_management
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Tạo bảng building
CREATE TABLE building (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    number_of_rooms INT NOT NULL,
    short_price FLOAT NOT NULL,
    mid_price FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- Tạo bảng room
CREATE TABLE room (
    id SERIAL PRIMARY KEY,
    building_id INT REFERENCES building(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    short_price FLOAT NOT NULL,
    mid_price FLOAT NOT NULL,
    area DECIMAL(10, 2),
    status VARCHAR(50) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- Tạo bảng image
CREATE TABLE image (
    id SERIAL PRIMARY KEY,
    room_id INT REFERENCES room(id) ON DELETE CASCADE,
    building_id INT REFERENCES building(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng tenant
CREATE TABLE tenant (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- Tạo bảng rental_agreement
CREATE TABLE rental_agreement (
    id SERIAL PRIMARY KEY,
    tenant_id INT REFERENCES tenant(id) ON DELETE CASCADE,
    room_id INT REFERENCES room(id) ON DELETE CASCADE,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    rent DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- Chèn dữ liệu vào bảng building
INSERT INTO building (name, address, number_of_rooms, short_price, mid_price)
VALUES
    ('サンライズビル', 'ホーチミン市1区ABC通り123番地', 10, 800000.00, 1000000.00),
    ('グリーンバレービル', 'ホーチミン市2区DEF通り456番地', 8, 1000000.00, 1200000.00),
    ('ブルースカイビル', 'ホーチミン市3区GHI通り789番地', 15, 1300000.00, 1500000.00);

-- Chèn dữ liệu vào bảng room
INSERT INTO room (building_id, name, type, short_price, mid_price, area, status)
VALUES
    (1, '101号室', 'シングル', 400000.00, 500000.00, 25.5, 'AVAILABLE'),
    (1, '102号室', 'ダブル', 450000.00, 550000.00, 30.0, 'RENTED'),
    (1, '103号室', 'スタジオ', 500000.00, 600000.00, 35.0, 'AVAILABLE'),
    (2, '201号室', 'シングル', 500000.00, 600000.00, 28.0, 'RENTED'),
    (2, '202号室', 'ダブル', 550000.00, 650000.00, 32.5, 'AVAILABLE'),
    (3, '301号室', 'スタジオ', 600000.00, 700000.00, 40.0, 'AVAILABLE'),
    (3, '302号室', 'シングル', 650000.00, 750000.00, 45.0, 'AVAILABLE');

-- Chèn dữ liệu vào bảng tenant
INSERT INTO tenant (full_name, email, phone, address)
VALUES
    ('グエン・ヴァン・ア', 'nguyenvana@example.com', '0123456789', 'ホーチミン市1区ABC通り123番地'),
    ('チャン・ティ・ビー', 'tranthib@example.com', '0987654321', 'ホーチミン市2区DEF通り456番地'),
    ('レー・クアン・シー', 'lequangc@example.com', '0111222333', 'ホーチミン市3区GHI通り789番地');

-- Chèn dữ liệu vào bảng rental_agreement
INSERT INTO rental_agreement (tenant_id, room_id, start_date, end_date, rent, status)
VALUES
    (1, 1, '2023-01-01', '2023-12-31', 500000.00, 'ACTIVE'),
    (2, 2, '2023-02-01', '2024-01-31', 550000.00, 'EXPIRED'),
    (3, 6, '2023-03-01', '2024-02-29', 700000.00, 'ACTIVE');

