import axios from 'axios';


export default {

  getCart() {
    return axios.get('/cart');
  },

  add(product) {
    const item = {
        productId: product.productId,
        quantity: 1
    };
    return axios.post(`/cart/items`, item);
  },

  deleteItem(id) {
    return axios.delete(`/cart/items/${id}`);
  },

  clearCart() {
    return axios.delete('/cart');
  }


}