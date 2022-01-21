package com.Swaroop.shopping.walletservice.service;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import com.Swaroop.shopping.walletservice.database.StatementRepository;
import com.Swaroop.shopping.walletservice.database.WalletRepository;
import com.Swaroop.shopping.walletservice.entity.Statement;
import com.Swaroop.shopping.walletservice.entity.Wallet;
import com.Swaroop.shopping.walletservice.entity.WalletRequest;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final StatementRepository statementRepository;

    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    public Wallet createWallet(Wallet wallet) {
        if (walletRepository.findByCustomerId(wallet.getCustomerId()).isEmpty()) {
            wallet.setWalletId(getNextId());
            return walletRepository.save(wallet);
        } else
            throw new IllegalArgumentException("Wallet already exist for customer " + wallet.getCustomerId());
    }

    @Synchronized
    public IllegalArgumentException addMoney(WalletRequest request) {
        Optional<Wallet> byWalletId = walletRepository.findByWalletId(request.getWalletId());
        if (byWalletId.isPresent()) {
            Wallet wallet1 = byWalletId.get();
            if (request.getTransactionType().equalsIgnoreCase("deposit")) {
                wallet1.setCurrentBalance(wallet1.getCurrentBalance() + request.getAmount());
            } else {
                if (wallet1.getCurrentBalance() < request.getAmount()) {
                    return new IllegalArgumentException("Wallet balance is not enough");
                }
                wallet1.setCurrentBalance(wallet1.getCurrentBalance() - request.getAmount());
            }
            walletRepository.save(wallet1);
        }
        return null;
    }

    public Wallet findByWalletId(int walletId) {
        return walletRepository.findByWalletId(walletId).orElse(Wallet.builder().build());
    }

    public Wallet findByCustomerId(String customerId) {
        return walletRepository.findByCustomerId(customerId).orElse(Wallet.builder().build());
    }

    public List<Statement> getStatementForWallet(int walletId) {
        return statementRepository.findByWalletId(walletId);
    }

    public List<Statement> getAllStatemet() {
        return statementRepository.findAll();
    }

    @Synchronized
    public void deleteWallet(int walletId) {
        Optional<Wallet> byWalletId = walletRepository.findByWalletId(walletId);
        if (byWalletId.isPresent()) {
            List<Statement> byWalletId1 = statementRepository.findByWalletId(walletId);
            statementRepository.deleteAll(byWalletId1);
            walletRepository.delete(byWalletId.get());
        }
    }

    @Synchronized
    public int getNextId() {
        Wallet wallet = walletRepository.findTopByOrderByWalletIdDesc();
        int id = (wallet != null) ? wallet.getWalletId() : 0;
        return ++id;
    }
}
