package com.hanghae.market.repository.chat;

import com.hanghae.market.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
