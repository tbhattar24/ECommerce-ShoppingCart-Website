import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {apiUrl} from "../../environments/environment";
import { Wallet } from '../models/Wallet';
import { JwtResponse } from '../response/JwtResponse';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private walletUrl = `${apiUrl}/wallet`;
  private currentUser: JwtResponse;

    constructor(private http: HttpClient,
      private userService: UserService) {
        this.userService.currentUser.subscribe(user => this.currentUser = user);
    }

    getWallet(): Observable<any> {
      let customer = this.currentUser._id;
      console.log(customer);
      return this.http.get(`${this.walletUrl}/customer/${customer}`).pipe(
        catchError(_ => of(null))
      );
  }

  createWallet(wallet) : Observable<Wallet> {
    return this.http.post(`${this.walletUrl}/createWallet`, wallet).pipe(
      catchError(_=> of(null))
    )
  }
}
