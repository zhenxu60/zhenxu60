<template>
  <section>
    <header>
      <h1>
        {{ product.name }}
      </h1>
      <button id="add-cart" title="Add item to cart" v-if="$store.state.token" v-on:click="addToCart">
        <font-awesome-icon class="icon action" icon="fa-solid fa-cart-plus"/>
        Add to Cart
      </button>
      
    </header>

    <div class="detail">
      <h2>{{ product.description }}</h2>
      <div class="sku">{{product.productSku}}</div>
      <div class="price">${{product.price}}</div>
      <div class="product-name">{{product.name}}</div>
      <div class="product-image">
        <img src="img\product_350x250.jpg" alt="Product photo" />
      </div>
    </div>
   

  </section>
</template>

<script>
import ProductService from "../services/ProductService.js";

export default {
  data() {
      return {
          product: {}
      }

  },

  methods: {
      getProductById(id) {
          ProductService.get(id)
        .then((response) => {
          this.product = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          console.log(error + " in getProductById()");
        });

      },
      addToCart() {
        window.alert(`Adding ${this.product.name} into cart`)
      }

      
  },

   created() {
    this.getProductById(this.$route.params.id);
  }
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.detail{
  width: 30%;
  padding: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "description description"
    "sku price"
    "name name"
    "image image";
}

h2 {
  grid-area: description;
}

.sku {
  grid-area: sku;
}

.price {
  grid-area: price;
}

.product-name {
  grid-area: name;
}

.product-image {
  grid-area: image;
}


</style>