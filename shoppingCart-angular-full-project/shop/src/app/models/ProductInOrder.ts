import {ProductInfo} from "./productInfo";

export class ProductInOrder {
    _id: string;
    name: string;
    price: number;
    countInStock: number;
    description: string;
    image: string;
    categoryType: number;
    count: number;

    constructor(productInfo:ProductInfo, quantity = 1){
        this._id = productInfo._id;
        this.name = productInfo.name;
        this.price = productInfo.price;
        this.countInStock = productInfo.countInStock;
        this.description = productInfo.description;;
        this.image = productInfo.image;
        this.categoryType = productInfo.categoryType;
        this.count = quantity;
    }
}
