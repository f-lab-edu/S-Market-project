package com.flab.s_market.domains.shopping.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingProduct is a Querydsl query type for ShoppingProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoppingProduct extends EntityPathBase<ShoppingProduct> {

    private static final long serialVersionUID = 414640952L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingProduct shoppingProduct = new QShoppingProduct("shoppingProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.s_market.domains.product.domain.QProduct product;

    public final NumberPath<Integer> productAmount = createNumber("productAmount", Integer.class);

    public final QShopping shopping;

    public QShoppingProduct(String variable) {
        this(ShoppingProduct.class, forVariable(variable), INITS);
    }

    public QShoppingProduct(Path<? extends ShoppingProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingProduct(PathMetadata metadata, PathInits inits) {
        this(ShoppingProduct.class, metadata, inits);
    }

    public QShoppingProduct(Class<? extends ShoppingProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.flab.s_market.domains.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
        this.shopping = inits.isInitialized("shopping") ? new QShopping(forProperty("shopping"), inits.get("shopping")) : null;
    }

}

