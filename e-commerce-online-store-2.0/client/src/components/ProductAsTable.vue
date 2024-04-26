<template>
  <section class="product-as-table">
    <thead>
      <tr>
        <th class="left-align">SKU</th>
        <th class="left-align">Product</th>
        <th class="right-align">Price</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="product in products" v-bind:key="product.productId">
        <td class="sku">{{ product.productSku }}</td>
        <td class="product-name" v-on:click="detail(product.productId)">{{ product.name }}</td>
        <td class="price right-align">${{ product.price }}</td>
        <td class="cart center-align">
          <button
            id="add-cart"
            title="Add item to cart"
            v-if="$store.state.token"
            v-on:click="add(product)"
          >
            <font-awesome-icon
              class="icon action"
              icon="fa-solid fa-cart-plus"
            />
          </button>
        </td>
      </tr>
    </tbody>
  </section>
</template>

<script>
import CartService from "../services/CartService.js";

export default {
  props: {
    products: {
      type: Array,
      required: true,
    }
  },

  methods: {
    detail(productId) {
      this.$router.push({ name: 'productDetails', params: { id: productId } });

    },

    add(product) {

      CartService.add(product)
        .then((response) => {
          if (response.status === 200) {
            window.alert("adding 1 item");
            this.$store.commit('SHOW_MESSAGE', product);
          }
        })
        .catch((error) => {
          console.log(error + " in add()");
        });
    }

  }
};
</script>

<style>
td {
    width: 20%;
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
</style>