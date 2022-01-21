import {Component, OnDestroy, OnInit} from '@angular/core';
// import {prod, products} from '../shared/mockData';
import {ProductService} from '../../services/product.service';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit, OnDestroy {


  title: string;
  page: any;
  private paramSub: Subscription;
  private querySub: Subscription;
  isReadMore = true


  constructor(private productService: ProductService,
              private route: ActivatedRoute) {

  }


  ngOnInit() {
    this.querySub = this.route.queryParams.subscribe(() => {
      this.update();
    });
    this.paramSub = this.route.params.subscribe(() => {
      this.update();
    });

  }

  ngOnDestroy(): void {
    this.querySub.unsubscribe();
    this.paramSub.unsubscribe();
  }

  update() {
    if (this.route.snapshot.queryParamMap.get('page')) {
      const currentPage = +this.route.snapshot.queryParamMap.get('page');
      const size = +this.route.snapshot.queryParamMap.get('size');
      this.getProds(currentPage, size);
    } else {
      this.getProds();
    }
  }
  getProds(page: number = 0, size: number = 3) {
    if (this.route.snapshot.url.length == 1) {
      this.productService.getAllInPage(page, size)
        .subscribe(page => {
          console.log(JSON.stringify(page))
          this.page = page;
          this.title = 'Hey! Here we go Some Latest Products';
        });
    } else { //  /category/:id
      const type = this.route.snapshot.url[1].path;
      this.productService.getCategoryInPage(type, page, size)
        .subscribe(categoryPage => {
          console.log(JSON.stringify(categoryPage));
          this.title = categoryPage.category;
          this.page = categoryPage.page;
        });
    }

  }

  showText() {
    this.isReadMore = !this.isReadMore
 }
}
