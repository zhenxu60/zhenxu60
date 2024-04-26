<template>
  <div class="product-as-card">
    <section id="introduction">
      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin elementum
        bibendum consequat. Sed non diam condimentum mauris tempor ullamcorper
        mattis porta nibh. Sed egestas, ante pulvinar pulvinar luctus, dolor
        arcu porttitor justo, ut sollicitudin quam lectus nec mi. Duis et
        sollicitudin leo, in mollis turpis. id quam risus. Phasellus vitae
        laoreet neque, quis iaculis lectus.
      </p>
    </section>

    <section id="product-cards">
      <article
        class="product-card"
        v-for="product in products"
        v-bind:key="product.productId"
      >
        <div class="sku">{{ product.productSku }}</div>
        <div class="price">${{ product.price }}</div>
        <div class="product-name" v-on:click="detail(product.productId)">
          {{ product.name }}
        </div>
        <div class="product-image">
          <img src="img\product_350x250.jpg" />
        </div>
        <div class="cart">
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
        </div>
      </article>
    </section>
  </div>
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
      this.$router.push({ name: "productDetails", params: { id: productId } });
    },

    add(product) {

      CartService.add(product)
        .then((response) => {
          if (response.status === 200) {
            // window.alert("adding 1 item");
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

<style scoped>
.product-as-card {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "intro intro intro intro"
    "cards cards cards cards";
  align-items: center;
}

#introduction {
  grid-area: intro;
}

#product-cards {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  grid-area: cards;
}

.product-card {
  border: 1px solid black;
  padding: 10px;
  margin: 10px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    "sku price"
    "name name"
    "image image"
    ". icon";
}

.sku {
  grid-area: sku;
  text-align: left;
}

.price {
  grid-area: price;
  text-align: right;
}

.product-name {
  grid-column: name;
}

.product-image {
  grid-area: image;
  text-align: center;
}

.product-image > img {
  border-radius: 30px;
}

.cart {
  grid-area: icon;
  text-align: right;
}
</style>