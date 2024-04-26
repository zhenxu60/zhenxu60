<template>
  <div id="cart-app">
    <header>
      <img src="img/logo_400x70.png" />
    </header>
    <nav>
      <div class="left">
        <router-link
          v-bind:to="{ name: 'home' }"
          class="link"
          v-bind:class="{ active: isActive('/') || isActive('/products') }"
          >Home</router-link
        >
        <router-link
          v-bind:to="{ name: 'cart' }"
          class="link"
          v-bind:class="{ active: isActive('/cart') }"
          >Cart</router-link
        >
      </div>

      <div class="message">
        <span class="message">{{
          this.$store.state.message
        }}</span>
      </div>

      <div class="right">
        <router-link
          class="link"
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token"
        >
          Logout
        </router-link>
        <router-link class="link" v-bind:to="{ name: 'login' }" v-else
          >Login</router-link
        >
      </div>
    </nav>
    <main>
      <router-view />
    </main>
    <footer>
      <p>&copy; 2022 All rights reserved.</p>
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showMessage: true,
    };
  },

  methods: {
    // Method to check if a route is active
    isActive(route) {
      return this.$route.path === route;
    },
  }

};
</script>

<style scoped>
#cart-app {
  height: 100vh;
  display: grid;
  grid-template-rows: 70px 20px 1fr 20px;
  grid-template-areas:
    "header"
    "nav"
    "product"
    "footer";
}

header {
  grid-area: header;
  border: 1px solid black;
  display: flex;
  justify-content: center;
}

nav {
  grid-area: nav;
  border: 1px solid black;
  border-top: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.active {
  color: blue;
}

.link {
  padding: 10px;
}

main {
  grid-area: product;
  overflow: scroll;
  align-items: center;
}

footer {
  grid-area: footer;
}
</style>