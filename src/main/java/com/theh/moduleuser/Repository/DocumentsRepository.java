package com.theh.moduleuser.Repository;


import com.theh.moduleuser.Model.Documents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentsRepository extends JpaRepository<Documents,Integer> {
    Page<Documents> findAll(Pageable pageable);

}
