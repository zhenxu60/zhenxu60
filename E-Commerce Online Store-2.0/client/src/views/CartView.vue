<template>
  <div id="cart-view">
    <div class="top-line">
      <h1>Shopping Cart</h1>
      <button
        id="delete-all"
        title="Delete all items from cart"
        v-on:click="clearAll"
      >
        <font-awesome-icon class="icon action" icon="fa-trash-can" />
        Clear Cart
      </button>
    </div>

    <table id="cart-table">
      <thead>
        <th class="left-align">Qty</th>
        <th class="left-align">Product</th>
        <th class="right-align">Price</th>
        <th class="right-align">Amount</th>
        <th></th>
      </thead>
      <tbody>
        <tr v-for="item in cart.items" v-bind:key="item.cartItemId">
          <td class="quantity">{{ item.quantity }}</td>
          <td class="product-name" v-on:click="detail(item.product.productId)">
            {{ item.product.name }}
          </td>
          <td class="price right-align">${{ item.product.price }}</td>
          <td class="amount right-align">
            ${{ item.quantity * item.product.price }}
          </td>
          <td class="center-align">
            <button
              id="delete-item"
              title="Delete item from cart"
              v-on:click="deleteItem(item.cartItemId)"
            >
              <font-awesome-icon class="icon action" icon="fa-solid fa-xmark" />
            </button>
          </td>
        </tr>
        <tr class="break-line">
          <td></td>
          <td>Item subtotal</td>
          <td></td>
          <td class="right-align">${{ cart.itemSubtotal }}</td>
        </tr>
        <tr>
          <td></td>
          <td>Tax</td>
          <td></td>
          <td class="right-align">${{ cart.tax }}</td>
        </tr>
        <tr>
          <td></td>
          <td>Total</td>
          <td></td>
          <td class="right-align">${{ cart.total }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CartService from "../services/CartService.js";

export default {
  data() {
    return {
      cart: {},
    };
  },

  methods: {
    detail(productId) {
      this.$router.push({ name: "productDetails", params: { id: productId } });
    },
    getCart() {
      CartService.getCart()
        .then((response) => {
          this.cart = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          console.log(error + " in getCart()");
        });
    },

    deleteItem(id) {
      CartService.deleteItem(id)
        .then((response) => {
          if (response.status === 204) {
            window.alert("deleting 1 item");
            this.getCart();
          }
        })
        .catch((error) => {
          console.log(error + " in deleteItem()");
        });
    },

    clearAll() {
      CartService.clearCart()
        .then((response) => {
          if (response.status === 204) {
            window.alert("deleting all items");
            this.getCart();
          }
        })
        .catch((error) => {
          console.log(error + " in clearAll()");
        });
    },
  },

  created() {
    this.getCart();
  },
};
</script>

<style>
.top-line {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.left-align {
  text-align: left;
}

.right-align {
  text-align: right;
}

.center-align {
  text-align: center;
}

.break-line {
  border: 5px solid red;
}
</style>