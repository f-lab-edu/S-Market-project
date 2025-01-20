DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id`	BIGINT	NOT NULL,
    `email`	VARCHAR(50)	NOT NULL,
    `name`	VARCHAR(20)	NOT NULL,
    `password`	VARCHAR(20)	NOT NULL,
    `email_key`	VARCHAR(20)	NOT NULL
);

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
    `id`	BIGINT	NOT NULL,
    `amount`	INT	NOT NULL,
    `price`	INT	NOT NULL,
    `name`	VARCHAR(50)	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL,
    `category_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `event`;

CREATE TABLE `event` (
    `id`	BIGINT	NOT NULL,
    `name`	VARCHAR(30)	NOT NULL,
    `start_date`	DATETIME	NULL,
    `end_date`	DATETIME	NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `shopping`;

CREATE TABLE `shopping` (
    `id`	BIGINT	NOT NULL,
    `user_id`	BIGINT	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
    `id`	BIGINT	NOT NULL,
    `name`	VARCHAR(20)	NULL,
    `parent_id`	BIGINT	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `term`;

CREATE TABLE `term` (
    `id`	BIGINT	NOT NULL,
    `url`	VARCHAR(255)	NULL,
    `title`	VARCHAR(80)	NOT NULL,
    `is_required`	BIT	NOT NULL,
    `additional`	BIT	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `user_sub_term`;

CREATE TABLE `user_sub_term` (
    `user_id`	BIGINT	NOT NULL,
    `version`	INTEGER	NOT NULL,
    `term_id`	BIGINT	NOT NULL,
    `agree_date`	DATETIME	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `shopping_product`;

CREATE TABLE `shopping_product` (
    `id`	BIGINT	NOT NULL,
    `product_amount`	INT	NOT NULL,
    `shopping_id`	BIGINT	NOT NULL,
    `product_id`	BIGINT	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
    `id`	BIGINT	NOT NULL,
    `order_date`	DATETIME	NOT NULL,
    `delivery_id`	BIGINT	NOT NULL,
    `shopping_id`	BIGINT	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `delivery`;

CREATE TABLE `delivery` (
    `id`	BIGINT	NOT NULL,
    `status`	VARCHAR(30)	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `sub_term`;

CREATE TABLE `sub_term` (
    `version`	INTEGER	NOT NULL,
    `term_id`	BIGINT	NOT NULL,
    `content`	VARCHAR(255)	NOT NULL,
    `created_at`	DATETIME	NOT NULL,
    `modified_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `event_product`;

CREATE TABLE `event_product` (
    `id`	BIGINT	NOT NULL,
    `product_id`	BIGINT	NOT NULL,
    `event_id`	BIGINT	NOT NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (`id`);

ALTER TABLE `product` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (`id`);

ALTER TABLE `event` ADD CONSTRAINT `PK_EVENT` PRIMARY KEY (`id`);

ALTER TABLE `shopping` ADD CONSTRAINT `PK_SHOPPING` PRIMARY KEY (`id`);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (`id`);

ALTER TABLE `term` ADD CONSTRAINT `PK_TERM` PRIMARY KEY (`id`);

ALTER TABLE `user_sub_term` ADD CONSTRAINT `PK_USER_SUB_TERM` PRIMARY KEY (
    `user_id`,
    `version`,
    `term_id`
    );

ALTER TABLE `shopping_product` ADD CONSTRAINT `PK_SHOPPING_PRODUCT` PRIMARY KEY (`id`);

ALTER TABLE `orders` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (`id`);

ALTER TABLE `delivery` ADD CONSTRAINT `PK_DELIVERY` PRIMARY KEY (`id`);

ALTER TABLE `sub_term` ADD CONSTRAINT `PK_SUB_TERM` PRIMARY KEY (
    `version`,
    `term_id`
    );

ALTER TABLE `event_product` ADD CONSTRAINT `PK_EVENT_PRODUCT` PRIMARY KEY (`id`);

ALTER TABLE `product` ADD CONSTRAINT `FK_category_TO_product_1` FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`);

ALTER TABLE `shopping` ADD CONSTRAINT `FK_user_TO_shopping_1` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`);

ALTER TABLE `category` ADD CONSTRAINT `FK_category_TO_category_1` FOREIGN KEY (`parent_id`)
    REFERENCES `category` (`id`);

ALTER TABLE `user_sub_term` ADD CONSTRAINT `FK_user_TO_user_sub_term_1` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`);

ALTER TABLE `user_sub_term` ADD CONSTRAINT `FK_sub_term_TO_user_sub_term_1` FOREIGN KEY (`version`, `term_id`)
    REFERENCES `sub_term` (`version`, `term_id`);

ALTER TABLE `shopping_product` ADD CONSTRAINT `FK_shopping_TO_shopping_product_1` FOREIGN KEY (`shopping_id`)
    REFERENCES `shopping` (`id`);

ALTER TABLE `shopping_product` ADD CONSTRAINT `FK_product_TO_shopping_product_1` FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`);

ALTER TABLE `orders` ADD CONSTRAINT `FK_delivery_TO_order_1` FOREIGN KEY (`delivery_id`)
    REFERENCES `delivery` (`id`);

ALTER TABLE `orders` ADD CONSTRAINT `FK_shopping_TO_order_1` FOREIGN KEY (`shopping_id`)
    REFERENCES `shopping` (`id`);

ALTER TABLE `sub_term` ADD CONSTRAINT `FK_Term_TO_sub_term_1` FOREIGN KEY (`term_id`)
    REFERENCES `term` (`id`);

ALTER TABLE `event_product` ADD CONSTRAINT `FK_product_TO_event_product_1` FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`);

ALTER TABLE `event_product` ADD CONSTRAINT `FK_event_TO_event_product_1` FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`);

