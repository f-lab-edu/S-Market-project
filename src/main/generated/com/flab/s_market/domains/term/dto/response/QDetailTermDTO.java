package com.flab.s_market.domains.term.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.flab.s_market.domains.term.dto.response.QDetailTermDTO is a Querydsl Projection type for DetailTermDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDetailTermDTO extends ConstructorExpression<DetailTermDTO> {

    private static final long serialVersionUID = 259077051L;

    public QDetailTermDTO(com.querydsl.core.types.Expression<String> url, com.querydsl.core.types.Expression<Integer> version, com.querydsl.core.types.Expression<String> title) {
        super(DetailTermDTO.class, new Class<?>[]{String.class, int.class, String.class}, url, version, title);
    }

}

