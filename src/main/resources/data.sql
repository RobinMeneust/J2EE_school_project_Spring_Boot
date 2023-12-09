INSERT INTO "LoyaltyProgram"("durationNbDays") VALUES(365);

INSERT INTO "Discount"("name", "startDate", "endDate", "discountPercentage") VALUES('Loyalty level reward', '2023-10-28', '2023-12-28', 10);
INSERT INTO "Discount"("name", "startDate", "endDate", "discountPercentage") VALUES('Loyalty level reward', '2023-10-28', '2023-12-28', 20);

INSERT INTO "Discount"("name", "startDate", "endDate", "discountPercentage") VALUES('Halloween sales', '2023-10-31', '2023-10-31', 15);

INSERT INTO "LoyaltyLevel"("requiredPoints", "idLoyaltyProgram", "idDiscount") VALUES(15,1,1);
INSERT INTO "LoyaltyLevel"("requiredPoints", "idLoyaltyProgram", "idDiscount") VALUES(25,1,2);

INSERT INTO "LoyaltyAccount"("loyaltyPoints", "endDate", "idLoyaltyProgram") VALUES(55,'2023-12-28', 1);
INSERT INTO "LoyaltyAccount"("loyaltyPoints", "endDate", "idLoyaltyProgram") VALUES(60,'2023-12-30', 1);

INSERT INTO "Address"("streetAddress", "postalCode", "city", "country") VALUES ('26 rue de la Mare', '34080', 'Montpellier', 'France');
INSERT INTO "Address"("streetAddress", "postalCode", "city", "country") VALUES ('33 rue Sadi Carnot', '32000', 'Auch', 'France');
INSERT INTO "Address"("streetAddress", "postalCode", "city", "country") VALUES ('60 rue de Lille', '91200', 'Athis-mons', 'France');
INSERT INTO "Address"("streetAddress", "postalCode", "city", "country") VALUES ('6 avenue de Provence', '26000', 'Valence', 'France');
INSERT INTO "Address"("streetAddress", "postalCode", "city", "country") VALUES ('18 Avenue Millies Lacroix', '78990', 'Élancourt', 'France');

INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('root', 'root', 'jeewebproject@gmail.com', '1000:65460f50162f1d94b21e59fb1c516338:fd32f025e305407e41daa397b16ebb183502f2a5e4aaaf6ab2ce427f04b1470f74c044e4b66e2ffeee6df7921b9be8631f3e03e01fa13609a3ef401c02049875'); -- @root123

INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('Robin', 'Meneust', 'robin@example.com', '1000:61f68dce346f7bf72925ec13259aa771:41027a218217d6bfaf0ab69951d1fed568159c180e58c94c2e08eaf272b9bf536bf52716c254d3f0598f9eb87585834b76ae935231f27a8ff9d8d372bab79191'); -- @robin123
INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('Lucas', 'Velay', 'lucas@example.com', '1000:2284d7f466ddab45de8e66cf31f9cd01:9814d3a4822ff220bc48fbd58ae28551fc37ba923d5363a1a98afdc3df258d8f66b797149b570c19bc8adb8f7ca37ce1ceb55e9a3eae135566953563fb16eb93'); -- @lucas123
INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('Jérémy', 'Saëlen', 'jeremy@example.com', '1000:7931d0679ddd85495fb3164ed9d9f035:0462d9322d5be6e390d4cdc2aefc54cd51f88d9271e330e6c92ba888ca974783b35ea58134d6329407bc95f024f87937fe362ad04506ba077de38f089b84d92f'); -- @jeremy123
INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('Théo', 'Gandy', 'theo@example.com', '1000:b777892652b967f9a2755b41293d305d:b8e9cf0a82df4449a54fdeea6eec824dadbe61ae4579d20880f752d8dede6e77830da6c7281fc01fbef615a4a76c67da80de17a4536a8182542b1d95faf19a50'); -- @theo123

INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('modo1', 'modo1', 'modo1@example.com', '1000:4201fdea5d6c99792fd016b927503c2c:e99384846781bef8d34ae1f410375162b3dc082245162f41f7d2d08269841dd372716ac8cc6aa2f182aab05f68e36eb4510481875da04e023a4140b0ca4f71cf'); -- @modo123
INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('modo2', 'modo2', 'modo2@example.com', '1000:64a3fce30ec521d5d40097c493688bf8:656037907c9966323e2beb3ee3c8be46e5f569f6e8b732a29f13bb94e3f6f325dd076cd5051a2d281dc20bd8608e2a36166e819c4aed5e1f68765299a5e91a58'); -- @modo234
INSERT INTO "User"("firstName", "lastName", "email", "password") VALUES ('modo3', 'modo3', 'modo3@example.com', '1000:c93a9f45bc613a3efc601d1d6f3eb701:b6a8f38f202ea47fd3b08d30ffeb0aeb197254a59800d1770721e5a75e3662fbafae7b6639ab4e39a6373e0399173b8c5c70d6a7085874c876e6e455dfa19942'); -- @modo345

INSERT INTO "Moderator"("idUser") VALUES(1);
INSERT INTO "Administrator"("idModerator") VALUES (1);

INSERT INTO "Moderator"("idUser") VALUES(6);
INSERT INTO "Moderator"("idUser") VALUES(7);
INSERT INTO "Moderator"("idUser") VALUES(8);

INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_DISCOUNT');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_MODERATOR');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_PRODUCT');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_CATEGORY');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_CUSTOMER');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_LOYALTY');
INSERT INTO "Permission"("permission") VALUES('CAN_MANAGE_ORDER');

INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(6,5); -- Can manage customer

INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(7,3); -- Can manage product
INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(7,4); -- Can manage category
INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(7,7); -- Can manage order

INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(8,6); -- Can manage loyalty
INSERT INTO "ModeratorPermission"("idModerator", "idPermission") VALUES(8,1); -- Can manage discount

INSERT INTO "Customer"("idUser", "idAddress", "idLoyaltyAccount") VALUES(2,1,1);
INSERT INTO "Customer"("idUser", "idAddress", "idLoyaltyAccount") VALUES(3,2,2);
INSERT INTO "Customer"("idUser", "idAddress", "idLoyaltyAccount") VALUES(4,3,NULL);
INSERT INTO "Customer"("idUser", "idAddress", "idLoyaltyAccount") VALUES(5,NULL,NULL);

INSERT INTO "Cart"("idCustomer") VALUES(3);

INSERT INTO "Category"("name", "description", "idDiscount") VALUES('strategy', 'A strategy game or strategic game is a game (e.g. a board game) in which the players'' uncoerced, and often autonomous, decision-making skills have a high significance in determining the outcome.', 1);
INSERT INTO "Category"("name", "description") VALUES('card game', 'A card game is any game using playing cards as the primary device with which the game is played, be they traditional or game-specific.');

INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Chess Board', 0, 15, 'A chessboard is a gameboard used to play chess. It consists of 64 squares, 8 rows by 8 columns, on which the chess pieces are placed. It is square in shape and uses two colours of squares, one light and one dark, in a chequered pattern. During play, the board is oriented such that each player''s near-right corner square is a light square.', 1, 'products/chess.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('UNO cards', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'products/uno_cards.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Werewolves', 20, 19, 'The Werewolves of Millers Hollow is a card game created by the french authors Philippe des Pallières and Hervé Marly in 2001.\n The game is based on the Russian game Mafia.', 2, 'products/werewolves.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Catan', 20, 34, 'Catan, previously known as The Settlers of Catan is a board game created by Klaus Teuber in 1995 in Germany.\n Players take on the roles of settlers attempting to build and develop holdings while trading and acquiring resources.', 1, 'products/catan.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Munchkin', 20, 23, 'Munchkin is a dedicated deck card game written by Steve Jackson and illustrated by John Kovalic.\n 3 to 6 players compete to kill monsters and grab magic items the first to reach level 10 wins.', 2, 'products/munchkin.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('7Wonders', 20, 45, '7wonders is a board game created by Antoine Bauza in 2010. Each player lead one of the 7 great cities of the Ancient World building their city to become the greatest.', 1, 'products/7wonders.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('9*9 Goban', 20, 55, 'A 9*9 Goban. A Goban in a gameboard used to pay Go', 1, 'products/goban.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Shogi', 20, 45, 'Shogi is a strategy board game. It is one the most popular board games in Japan and is in the same family of games as Western xhess, chanturanga, Indian chess and janggi.', 1, 'products/shogi.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Playing cards', 100, 5, 'A set of playing cards', 2, 'products/playing_cards.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Tarot cards', 100, 5, 'A set of tarot cards', 2, 'products/tarot.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Take 6', 100, 5, 'Take 6! is a card game for 2 to 10 players designed by Wolfgang Kramer in 1994 and published by Amigo Spiele.', 2, 'products/take6.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Risk', 30, 24, 'Risk is a strategy war game invented in 1957 by Albert Lamorise, a French filmmaker and is one of the most popular board games in history', 1, 'products/risk.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Ticket to ride', 50, 39.99, 'Ticket to ride is a railway-themed German-style board game designed by Alan R. Moon. Players collect and play train cards to claim train routes across the map. The goal is to create the longest continuous railway and connect distant cities to earn the most points.', 1, 'products/Ticket_to_Ride.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Exploding Kittens', 100, 11, 'Exploding Kittens is a card game designed by Matthew Inman, Elan Lee and Shane Small and began as a Kickstarter project in 2015.', 2, 'products/exploding_kittens.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Spot it', 100, 15, 'Spot it is a game in which players have to find symbols in common between two cards.', 2, 'products/spot_it.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Monopoly', 30, 20, 'Monopoly is a multiplayer themed board game hare players buy and trade properties with the goal of driving their opponents into bankruptcy.', 1, 'products/monopoly.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Civilization the board game', 30, 75,'Sid Meier Civilization: The Board Game is a 2010 board game created by Kevin Wilson based on the Sid Meier Civilization series of video games.', 1, 'products/Civilization.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Battleship', 30, 20,'Battleship is a two players strategy game played on ruled grids on which each player fleet of warships are marked.\n The locations of the fleets are concealed from the other player and the objective of the game is to destroy the opposing player fleet', 1, 'products/battleship.jpg');
INSERT INTO "Product"("name", "stockQuantity", "unitPrice", "description", "idCategory", "imagePath") VALUES('Connect 4', 200, 11.99,'Connect 4 is a game in which players take turns dropping colored tokens into a six-row, seven-column vertically suspended grid. The objective of the game is to be the first one to form a horizontal, vertical or diagonal line of four of one own tokens.', 1, 'products/connect4.jpg');



INSERT INTO "CartItem"("quantity", "idCart", "idProduct") VALUES(1,1,1);
INSERT INTO "CartItem"("quantity", "idCart", "idProduct") VALUES(3,1,2);

INSERT INTO "LoyaltyAccountLevelUsed"("idLoyaltyAccount", "idLoyaltyLevel") VALUES(1,1);

INSERT INTO "Mail"("fromAddress", "toAddress", "subject", "body", "date") VALUES('example@example.com', 'example@example.com', 'Test mail', 'This is the body of a mail used for testing purposes', '2023-10-30');


INSERT INTO "Orders"("date", "orderStatus", "idCustomer", "idAddress") VALUES('2023-10-30', 'SHIPPED', 2, 1);
INSERT INTO "OrderItem"("quantity", "idOrder", "idProduct","total") VALUES(2,1,1,2);

INSERT INTO "FeaturedProduct"("idProduct") VALUES(1);
INSERT INTO "FeaturedProduct"("idProduct") VALUES(2);
INSERT INTO "FeaturedProduct"("idProduct") VALUES(3);
INSERT INTO "FeaturedProduct"("idProduct") VALUES(4);
INSERT INTO "FeaturedProduct"("idProduct") VALUES(5);