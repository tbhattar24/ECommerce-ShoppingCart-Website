import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Wallet } from 'src/app/models/Wallet';
import { JwtResponse } from 'src/app/response/JwtResponse';
import { UserService } from 'src/app/services/user.service';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  currentUser: JwtResponse;
  wallet: Wallet;
  constructor(private httpClient: HttpClient,
    private walletService: WalletService,
    private userService: UserService,
    private route: ActivatedRoute) { } 

    querySub: Subscription;
    ngOnInit() {
      this.currentUser = this.userService.currentUserValue;
      this.querySub = this.route.queryParams.subscribe(() => {
          this.update();
      });

  }

  update() {
      this.walletService.getWallet().subscribe(wallet => this.wallet = wallet, _ => {
          console.log("Get Wallet Failed")
      });
  }

  createWallet() {
    let wallet = {
      id: null,
      walletId: 0,
      customerId: this.currentUser._id,
      currentBalance: 5000.0
    }
    this.walletService.createWallet(wallet).subscribe(wallet => this.wallet = wallet, _=> {
      console.log("wallet creation failed");
    })
  }


}
