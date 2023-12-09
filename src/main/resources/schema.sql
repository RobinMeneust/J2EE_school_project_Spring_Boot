CREATE TABLE IF NOT EXISTS "Address" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "streetAddress" VARCHAR(60) NOT NULL,
    "postalCode" VARCHAR(30) NOT NULL,
    "city" VARCHAR(60) NOT NULL,
    "country" VARCHAR(60) NOT NULL,
    UNIQUE("streetAddress", "postalCode", "city", "country")
);

CREATE TABLE IF NOT EXISTS "User" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "firstName" VARCHAR(30),
    "lastName" VARCHAR(30),
    "email" VARCHAR(50) NOT NULL UNIQUE,
    "password" VARCHAR(168) NOT NULL,
    "phoneNumber" VARCHAR(15) UNIQUE
);

CREATE TABLE IF NOT EXISTS "Moderator" (
    "idUser" INT PRIMARY KEY ,
    FOREIGN KEY ("idUser") REFERENCES "User"("id")
);

CREATE TABLE IF NOT EXISTS "Administrator" (
    "idModerator" INT PRIMARY KEY,
    FOREIGN KEY ("idModerator") REFERENCES "Moderator"("idUser")
);

CREATE TABLE IF NOT EXISTS "Discount" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "name" VARCHAR(30),
    "startDate" DATE NOT NULL,
    "endDate" DATE NOT NULL,
    "discountPercentage" INT NOT NULL,
    CONSTRAINT "valid_percentage" CHECK ("discountPercentage" > 0 AND "discountPercentage" <= 100),
    CONSTRAINT "valid_dates" CHECK ("startDate" <= "endDate")
);

CREATE TABLE IF NOT EXISTS "Category" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "name" VARCHAR(30) UNIQUE NOT NULL,
    "description" VARCHAR(300),
    "idDiscount" INT DEFAULT NULL,
    FOREIGN KEY ("idDiscount") REFERENCES "Discount"("id")
);

CREATE TABLE IF NOT EXISTS "LoyaltyProgram" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "durationNbDays" INT NOT NULL,
    CONSTRAINT "valid_duration" CHECK ("durationNbDays" > 0)
);

CREATE TABLE IF NOT EXISTS "LoyaltyAccount" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "loyaltyPoints" INT NOT NULL,
    "endDate" DATE NOT NULL,
    "idLoyaltyProgram" INT NOT NULL,
    FOREIGN KEY ("idLoyaltyProgram") REFERENCES "LoyaltyProgram"("id")
);

CREATE TABLE IF NOT EXISTS "LoyaltyLevel" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "requiredPoints" INT NOT NULL,
    "idLoyaltyProgram" INT NOT NULL,
    "idDiscount" INT NOT NULL,
    FOREIGN KEY ("idLoyaltyProgram") REFERENCES "LoyaltyProgram"("id"),
    FOREIGN KEY ("idDiscount") REFERENCES "Discount"("id"),
    CONSTRAINT "valid_required_points_nb" CHECK ("requiredPoints" >= 0)
);

CREATE TABLE IF NOT EXISTS "LoyaltyAccountDiscounts" (
    "idLoyaltyAccount" INT NOT NULL,
    "idDiscount" INT NOT NULL,
    FOREIGN KEY ("idLoyaltyAccount") REFERENCES "LoyaltyAccount"("id"),
    FOREIGN KEY ("idDiscount") REFERENCES "Discount"("id"),
    PRIMARY KEY("idLoyaltyAccount", "idDiscount")
);

CREATE TABLE IF NOT EXISTS "LoyaltyAccountLevelUsed" (
    "idLoyaltyAccount" INT NOT NULL,
    "idLoyaltyLevel" INT NOT NULL,
    FOREIGN KEY ("idLoyaltyAccount") REFERENCES "LoyaltyAccount"("id"),
    FOREIGN KEY ("idLoyaltyLevel") REFERENCES "LoyaltyLevel"("id"),
    PRIMARY KEY("idLoyaltyAccount", "idLoyaltyLevel")
);

CREATE TABLE IF NOT EXISTS "Product" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "name" VARCHAR(30),
    "stockQuantity" INT NOT NULL DEFAULT 0,
    "unitPrice" FLOAT NOT NULL,
    "description" VARCHAR(1000),
    "imagePath" VARCHAR(500),
    "weight" FLOAT,
    "idCategory" INT NOT NULL,
    FOREIGN KEY ("idCategory") REFERENCES "Category"("id"),
    CONSTRAINT "valid_unit_price" CHECK("unitPrice" >= 0),
    CONSTRAINT "valid_stock_quantity" CHECK("stockQuantity" >= 0)
);

CREATE TABLE IF NOT EXISTS "FeaturedProduct" (
    "idProduct" INT PRIMARY KEY,
    FOREIGN KEY ("idProduct") REFERENCES "Product"("id")
);

CREATE TABLE IF NOT EXISTS "Customer" (
    "idUser" INT PRIMARY KEY,
    "idAddress" INT DEFAULT NULL,
    "idLoyaltyAccount" INT DEFAULT NULL UNIQUE,
    FOREIGN KEY ("idUser") REFERENCES "User"("id"),
    FOREIGN KEY ("idAddress") REFERENCES "Address" ("id"),
    FOREIGN KEY ("idLoyaltyAccount") REFERENCES "LoyaltyAccount" ("id")
);


CREATE TABLE IF NOT EXISTS "Orders" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "date" DATE NOT NULL,
    "orderStatus" VARCHAR(30) NOT NULL,
    "idCustomer" INT NOT NULL,
    "idAddress" INT NOT NULL,
    "idDiscount" INT DEFAULT NULL,
    FOREIGN KEY ("idDiscount") REFERENCES "Discount"("id"),
    FOREIGN KEY ("idCustomer") REFERENCES "Customer"("idUser"),
    FOREIGN KEY ("idAddress") REFERENCES "Address"("id")
);

CREATE TABLE IF NOT EXISTS "Cart" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "idDiscount" INT DEFAULT NULL,
    "idCustomer" INT NOT NULL UNIQUE,
    FOREIGN KEY ("idDiscount") REFERENCES "Discount"("id"),
    FOREIGN KEY ("idCustomer") REFERENCES "Customer"("idUser")
);

CREATE TABLE IF NOT EXISTS "CartItem" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "quantity" INT NOT NULL,
    "idCart" INT,
    "idProduct" INT NOT NULL,
    FOREIGN KEY ("idCart") REFERENCES "Cart"("id"),
    FOREIGN KEY ("idProduct") REFERENCES "Product"("id"),
    CONSTRAINT "valid_quantity_cart_item" CHECK("quantity" >= 0)
);

CREATE TABLE IF NOT EXISTS "OrderItem" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "quantity" INT NOT NULL,
    "idOrder" INT,
    "idProduct" INT NOT NULL,
    "total" FLOAT NOT NULL, -- Price with discounts and considering the quantity (exact payed price)
    FOREIGN KEY ("idOrder") REFERENCES "Orders"("id"),
    FOREIGN KEY ("idProduct") REFERENCES "Product"("id"),
    CONSTRAINT "valid_quantity_order" CHECK("quantity" >= 0)
);

CREATE TABLE IF NOT EXISTS "Mail" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "fromAddress" VARCHAR(50) NOT NULL,
    "toAddress" VARCHAR(50) NOT NULL,
    "subject" VARCHAR(64) NOT NULL,
    "body" VARCHAR(512) NOT NULL,
    "date" Date NOT NULL
);

CREATE TABLE IF NOT EXISTS "Permission" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "permission" VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "ModeratorPermission" (
    "idModerator" INT NOT NULL,
    "idPermission" INT NOT NULL,
    FOREIGN KEY ("idModerator") REFERENCES "Moderator"("idUser"),
    FOREIGN KEY ("idPermission") REFERENCES "Permission"("id"),
    PRIMARY KEY("idModerator", "idPermission")
);

CREATE TABLE IF NOT EXISTS "ForgottenPassword" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "idUser" INT NOT NULL,
    "token" VARCHAR(50) NOT NULL,
    "expiryDate" DATETIME NOT NULL,
    FOREIGN KEY ("idUser") REFERENCES "User"("id")
);