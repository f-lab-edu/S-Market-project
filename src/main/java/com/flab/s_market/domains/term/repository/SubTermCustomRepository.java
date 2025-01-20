package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.dto.response.DetailTermDTO;

public interface SubTermCustomRepository {
    public DetailTermDTO findByVersionAndUrl(Integer version);
}
