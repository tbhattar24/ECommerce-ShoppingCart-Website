import { ProductInOrder } from "./ProductInOrder";

export class Order {
    orderId: number;
    buyerEmail: string;
    buyerName: string;
    buyerPhone: string;
    buyerAddress: string;
    orderAmount: string;
    orderStatus: string;
    createTime: string;
    updateTime: string;
    productInOrder: ProductInOrder;
}
