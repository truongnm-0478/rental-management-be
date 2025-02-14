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

CREATE TABLE building (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    number_of_rooms INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,  -- Giá tiền tháng
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE room (
    id SERIAL PRIMARY KEY,
    building_id INT REFERENCES building(id) ON DELETE CASCADE, -- Mối quan hệ với tòa nhà
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,  -- Giá tiền tháng của phòng
    area DECIMAL(10, 2),  -- Diện tích phòng
    status VARCHAR(50) DEFAULT 'available',  -- Trạng thái phòng (available, rented, etc.)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE image (
    id SERIAL PRIMARY KEY,
    room_id INT REFERENCES room(id) ON DELETE CASCADE,  -- Mối quan hệ với phòng
    building_id INT REFERENCES building(id) ON DELETE CASCADE,  -- Mối quan hệ với tòa nhà
    url TEXT NOT NULL,  -- Đường dẫn ảnh
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tenant (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rental_agreement (
    id SERIAL PRIMARY KEY,
    tenant_id INT REFERENCES tenant(id) ON DELETE CASCADE,  -- Mối quan hệ với người thuê
    room_id INT REFERENCES room(id) ON DELETE CASCADE,  -- Mối quan hệ với phòng
    start_date TIMESTAMP NOT NULL,  -- Ngày bắt đầu thuê
    end_date TIMESTAMP NOT NULL,  -- Ngày kết thúc thuê
    rent DECIMAL(10, 2) NOT NULL,  -- Giá thuê phòng
    status VARCHAR(50) DEFAULT 'active',  -- Trạng thái hợp đồng (active, expired)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chèn dữ liệu vào bảng building
INSERT INTO building (name, address, number_of_rooms, price)
VALUES
    ('Tòa nhà Sunrise', '123 Đường ABC, Quận 1, TP.HCM', 10, 1000000.00),
    ('Tòa nhà Green Valley', '456 Đường DEF, Quận 2, TP.HCM', 8, 1200000.00),
    ('Tòa nhà Blue Sky', '789 Đường GHI, Quận 3, TP.HCM', 15, 1500000.00);

-- Chèn dữ liệu vào bảng room
INSERT INTO room (building_id, name, price, area, status)
VALUES
    (1, 'Phòng 101', 500000.00, 25.5, 'available'),
    (1, 'Phòng 102', 550000.00, 30.0, 'rented'),
    (1, 'Phòng 103', 600000.00, 35.0, 'available'),
    (2, 'Phòng 201', 600000.00, 28.0, 'rented'),
    (2, 'Phòng 202', 650000.00, 32.5, 'available'),
    (3, 'Phòng 301', 700000.00, 40.0, 'available'),
    (3, 'Phòng 302', 750000.00, 45.0, 'available');

-- Chèn dữ liệu vào bảng tenant
INSERT INTO tenant (full_name, email, phone, address)
VALUES
    ('Nguyễn Văn A', 'nguyenvana@example.com', '0123456789', '123 Đường ABC, Quận 1, TP.HCM'),
    ('Trần Thị B', 'tranthib@example.com', '0987654321', '456 Đường DEF, Quận 2, TP.HCM'),
    ('Lê Quang C', 'lequangc@example.com', '0111222333', '789 Đường GHI, Quận 3, TP.HCM');

-- Chèn dữ liệu vào bảng rental_agreement
INSERT INTO rental_agreement (tenant_id, room_id, start_date, end_date, rent, status)
VALUES
    (1, 1, '2023-01-01', '2023-12-31', 500000.00, 'active'),
    (2, 2, '2023-02-01', '2024-01-31', 550000.00, 'expired'),
    (3, 6, '2023-03-01', '2024-02-29', 700000.00, 'active');






