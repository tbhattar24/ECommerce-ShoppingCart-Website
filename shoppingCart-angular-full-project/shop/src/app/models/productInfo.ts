import {ProductInOrder} from "./ProductInOrder";

export class ProductInfo {
    _id: string;
    name: string;
    price: number;
    countInStock: number;
    description: string;
    image: string;
    productStatus: number; // 0: onsale 1: offsale
    categoryType: number;
    createTime: string;
    updateTime: string;


    constructor(productInOrder?: ProductInOrder) {
        if (productInOrder) {
            this._id = productInOrder._id;
            this.name = productInOrder.name;
            this.price = productInOrder.price;
            this.countInStock = productInOrder.countInStock;
            this.description = productInOrder.description;
            this.image = productInOrder.image;
            this.categoryType = productInOrder.categoryType;
            this.productStatus = 0;
        } else {
            this._id = '';
            this.name = '';
            this.price = 20;
            this.countInStock = 100;
            this.description = '';
            this.image = '';
            this.categoryType = 0;
            this.productStatus = 0;
        }
    }
}

