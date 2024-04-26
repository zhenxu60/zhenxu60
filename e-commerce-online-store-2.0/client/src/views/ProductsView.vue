<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Products
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <div id="right-align">
        <font-awesome-icon
          id="switch"
          v-bind:class="{ 'view-icon': true, active: cardView }"
          v-on:click="cardView = true"
          icon="fa-solid fa-grip"
          title="View tiles"
        />
        <font-awesome-icon
          id="switch"
          v-bind:class="{ 'view-icon': true, active: !cardView }"
          v-on:click="cardView = false"
          icon="fa-solid fa-table"
          title="View table"
        />
        <!-- add searchBar -->
        <div id="search-bar">
          <input
            id="search-text"
            type="text"
            v-model="filter"
            v-on:keyup.enter="getProducts"
            placeholder="Search..."
          />
          <font-awesome-icon
            icon="fa-solid fa-magnifying-glass"
            class="Search product"
            v-on:click="getProducts"
          />
        </div>
      </div>
    </div>

    <p id="login-message" v-if="!isLoggedIn">
      Welcome! You may browse anonymously as much as you wish, but you must
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
      items to your shopping cart.
    </p>


    <!-- add product as card -->
    <product-as-card
      v-bind:products="products"
      v-if="cardView"
    ></product-as-card>

    <!-- add product as table -->
    <product-as-table v-bind:products="products" v-else></product-as-table>
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ProductService from "../services/ProductService.js";
import ProductAsCard from "../components/ProductAsCard.vue";
import ProductAsTable from "../components/ProductAsTable.vue";

export default {
  components: {
    LoadingSpinner,
    ProductAsCard,
    ProductAsTable,
  },
  data() {
    return {
      isLoading: false,
      cardView: true,
      products: [],
      filter: "",
    };
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },

  methods: {
    // get products
    getProducts() {
      if (this.filter) {
        this.searchProducts();
        return;
      }

      ProductService.list()
        .then((response) => {
          this.products = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          console.log(error + " in getProducts()");
        });
    },

    searchProducts() {
    ProductService.searchProduct(this.filter)
    .then((response) => {
          this.products = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          console.log(error + " in searchProducts()");
        });
  }
  },
  created() {
    this.getProducts();
  },
};
</script>

<style scoped>
#heading-line {
  padding: 10px;
  display: flex;
  justify-content: space-between;
}

#spinner {
  color: green;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}

#search-bar {
  border: 1px solid #ccc;
  display: inline-block;
}
#search-text {
  border: none;
}

@media only screen and (max-width: 600px) {
 #switch {
   visibility: hidden;

 }
}

</style>