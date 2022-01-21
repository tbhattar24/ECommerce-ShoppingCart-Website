import {Component, OnInit} from '@angular/core';
import {Observable, Subject, Subscription} from "rxjs";
import {OrderService} from "../../services/order.service";
import {Order} from "../../models/Order";
import {ActivatedRoute} from "@angular/router";
import { debounceTime, switchMap } from 'rxjs/operators';
import { JwtResponse } from 'src/app/response/JwtResponse';
import { Role } from 'src/app/enum/Role';
import { UserService } from 'src/app/services/user.service';
import { OrderStatus } from 'src/app/enum/OrderStatus';

@Component({
    selector: 'app-order-detail',
    templateUrl: './order-detail.component.html',
    styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

    currentUser: JwtResponse;
    Role = Role;
    OrderStatus = OrderStatus;
    constructor(private orderService: OrderService,
                private route: ActivatedRoute,
                private userService: UserService,) {
    }

    order$: any;
    private updateTerms = new Subject<Order>();
    sub: Subscription;

    ngOnInit() {
        this.currentUser = this.userService.currentUserValue;
        this.sub = this.route.queryParams.subscribe(() => {
            this.update();
        });
        // this.items$ = this.route.paramMap.pipe(
        //     map(paramMap =>paramMap.get('id')),
        //     switchMap((id:string) => this.orderService.show(id))
        // )
        //this.order$ = this.orderService.show(this.route.snapshot.paramMap.get('id'));
    }

    update() {

        this.orderService.show(this.route.snapshot.paramMap.get('id')).subscribe(order => this.order$ = order, _ => {
            console.log("Get Orde Failed")
        });
    }

}
