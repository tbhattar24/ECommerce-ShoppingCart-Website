package com.Swaroop.shopping.walletservice.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Swaroop.shopping.walletservice.entity.Statement;

import java.util.List;

@Repository
public interface StatementRepository extends MongoRepository<Statement, String> {

    List<Statement> findByWalletId(int walletId);
}
