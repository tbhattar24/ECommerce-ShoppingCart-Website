import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { OrderStatus } from 'src/app/enum/OrderStatus';
import { Role } from 'src/app/enum/Role';
import { Wallet } from 'src/app/models/Wallet';
import { JwtResponse } from 'src/app/response/JwtResponse';
import { OrderService } from 'src/app/services/order.service';
import { UserService } from 'src/app/services/user.service';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  currentUser: JwtResponse;
  Role = Role;
  OrderStatus = OrderStatus;
  wallet: Wallet;
  constructor(private orderService: OrderService,
              private route: ActivatedRoute,
              private userService: UserService,
              private walletService: WalletService,) {
  }
  order$: any;
  sub: Subscription;
  paymentStatus: any;

    ngOnInit() {
        this.currentUser = this.userService.currentUserValue;
        this.sub = this.route.queryParams.subscribe(() => {
            this.update();
        });
    }

    update() {

        this.orderService.show(this.route.snapshot.paramMap.get('id'))
        .subscribe(
          order => this.order$ = order, _ => 
          {
            console.log("Get Orde Failed")
        });

        this.walletService.getWallet().subscribe(wallet => this.wallet = wallet, _ => {
          console.log("Get Wallet Failed")
      });
    }

    payFromWallet() {
      this.orderService.payOnline(this.order$)
      .subscribe(
        order => this.order$ = order, _=> {
          console.log("Payment Failed")
        }
      );

      this.walletService.getWallet().subscribe(wallet => this.wallet = wallet, _ => {
        console.log("Get Wallet Failed")
    });

    }

}
