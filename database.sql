-- Tạo bảng người dùng
CREATE TABLE tblUser (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(25) NOT NULL,
    Password VARCHAR(25) NOT NULL,
    fullName VARCHAR(50) NOT NULL,
    Role VARCHAR(10) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    emailAddress VARCHAR(50) NOT NULL
);

-- Tạo bảng đại lý phụ
CREATE TABLE tblSubAgent (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    brandName VARCHAR(50) NOT NULL,
    Address VARCHAR(250) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL
);

-- Tạo bảng sản phẩm
CREATE TABLE tblItem (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(50) NOT NULL,
    Description VARCHAR(250),
    unitPrice FLOAT(10) NOT NULL
);

-- Tạo bảng hóa đơn xuất
CREATE TABLE tblExportBill (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    dateIssued DATE NOT NULL,
    UserID INT(10) NOT NULL,
    SubAgentID INT(10) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES tblUser(ID),
    FOREIGN KEY (SubAgentID) REFERENCES tblSubAgent(ID)
);

-- Tạo bảng chi tiết sản phẩm xuất
CREATE TABLE tblExportedItem (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Quantity INT(10) NOT NULL,
    unitPrice FLOAT(10) NOT NULL,
    ItemID INT(10) NOT NULL,
    ExportBillID INT(10) NOT NULL,
    FOREIGN KEY (ItemID) REFERENCES tblItem(ID),
    FOREIGN KEY (ExportBillID) REFERENCES tblExportBill(ID)
);

CREATE TABLE tblImportBill (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    dateIssued DATE NOT NULL,
    UserID INT(10) NOT NULL,
    SubAgentID INT(10) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES tblUser(ID),
    FOREIGN KEY (SubAgentID) REFERENCES tblSubAgent(ID)
);

CREATE TABLE tblImportedItem (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Quantity INT(10) NOT NULL,
    unitPrice FLOAT(10) NOT NULL,
    ItemID INT(10) NOT NULL,
    Importbillid INT(10) NOT NULL,
    FOREIGN KEY (ItemID) REFERENCES tblItem(ID),
    FOREIGN KEY (Importbillid) REFERENCES tblImportBill(ID)
);

-- Test data
INSERT INTO tblUser (ID, Username, Password, fullName, Role, phoneNumber, emailAddress) VALUES
(1, 'A', '123', 'Nguyen Van A', 'stockclerk', '0987654321', 'A@gmail.com'),
(2, 'C', '123', 'Nguyen Van C', 'stockclerk', '0192837465', 'C@gmai.com'),
(3, 'B', 'M123', 'Nguyen Thi B', 'Manager', '0123456789', 'B@gmail.com');


INSERT INTO tblsubagent (brandName, Address, phoneNumber) VALUES
('LaptopVN', '150 Nguyen Trai, Ha Noi', '0987897891'),
('LaptopVN Distribution', '12 Tran Phu, Ha Noi', '0981234567'),
('HCM LaptopVN', '99 Le Loi, Ho Chi Minh City', '0901122334'),
('Laptop World VN', '23 Hai Ba Trung, Ha Noi', '0912345678'),
('ABC Distribution', '10 Tran Hung Dao, Ha Noi', '0945678901');

INSERT INTO tblItem (ID, itemName, Description, unitPrice) VALUES
(1, 'Asus Nitro 5', 'i5-11400H, 8GB RAM, 3050 RTX, 512GB SSD', 10000),
(2, 'NitrinTro', 'Limited Edition, 32GB RAM, 2TB SSD', 4000),
(3, 'Nitro Gaming Mouse 2', 'RGB, 16000 DPI, USB, No Delay', 1000),
(4, 'Nitro 5.5EX', 'i5-12700H, 16GB RAM, 3060Ti RTX, 1TB SSD', 20000),
(5, 'Asus TUF', 'i7-10750H, 16GB RAM, 2060 RTX, 512GB SSD', 15000),
(6, 'Lenovo Legion 5', 'Ryzen 5, 8GB RAM, 1650 GTX, 512GB SSD', 25000),
(7, 'Gaming Mouse Pro Nitro', 'Luxury Gaming Mouse, 8000DPI, USB, LED RGB', 2000);

INSERT INTO tblExportBill (ID, dateIssued, UserID, SubAgentID) VALUES
(1, STR_TO_DATE('04/03/2025', '%d/%m/%Y'), 1, 1),
(2, STR_TO_DATE('09/03/2025', '%d/%m/%Y'), 1, 2),
(3, STR_TO_DATE('20/03/2025', '%d/%m/%Y'), 1, 3),
(4, STR_TO_DATE('01/04/2025', '%d/%m/%Y'), 2, 4),
(5, STR_TO_DATE('02/04/2025', '%d/%m/%Y'), 2, 5);

INSERT INTO tblExportedItem (ID, ItemID, ExportBillID, Quantity, unitPrice) VALUES
(1, 1, 1, 2, 20000),
(2, 2, 1, 1, 80000),
(3, 3, 2, 5, 2500),
(4, 4, 2, 3, 15000),
(5, 3, 3, 10, 15000),
(6, 5, 4, 5, 25000),
(7, 6, 4, 5, 30000),
(8, 1, 5, 4, 20000),
(9, 7, 5, 4, 4000);

INSERT INTO tblImportbill (ID, dateIssued, UserID, SubAgentID) VALUES
(1, STR_TO_DATE('04/03/2025', '%d/%m/%Y'), 1, 1),
(2, STR_TO_DATE('09/03/2025', '%d/%m/%Y'), 1, 2),
(3, STR_TO_DATE('20/03/2025', '%d/%m/%Y'), 1, 3),
(4, STR_TO_DATE('01/04/2025', '%d/%m/%Y'), 2, 4),
(5, STR_TO_DATE('02/04/2025', '%d/%m/%Y'), 2, 5);

INSERT INTO tblimporteditem (ID, ItemID, ImportbillId, Quantity, unitPrice) VALUES
(1, 1, 1, 20, 20000),
(2, 2, 1, 10, 80000),
(3, 3, 2, 50, 2500),
(4, 4, 2, 30, 15000),
(5, 3, 3, 20, 15000),
(6, 5, 4, 50, 25000),
(7, 6, 4, 50, 30000),
(8, 1, 5, 40, 20000),
(9, 7, 5, 40, 4000);



ALter table tblitem add column unitPrice FLOAT(10) NOT NULL;

use exporting;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE tbluser;
SET FOREIGN_KEY_CHECKS = 1;