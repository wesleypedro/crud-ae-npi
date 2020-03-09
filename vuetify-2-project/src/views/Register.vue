<template>
  <v-container fluid class="fill-height">
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>Cadastro</v-toolbar-title>
          </v-toolbar>
          <v-form @submit.prevent="onSubmit" ref="form" lazy-validation v-model="valid">
            <v-card-text>
              <p class="error--text text-center">{{error}}</p>
              <v-text-field
                label="Nome"
                name="nome"
                prepend-icon="person"
                type="text"
                v-model="nome"
                required
                :rules="requiredRule"
              />
              <v-text-field
                label="Login"
                name="email"
                prepend-icon="email"
                type="text"
                v-model="email"
                required
                :rules="emailRules"
              />
              <v-text-field
                id="password"
                label="Password"
                name="password"
                prepend-icon="lock"
                type="password"
                v-model="password"
                required
                :rules="requiredRule"
              />
              <v-checkbox v-model="habilitado" label="Ativo"></v-checkbox>
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" href="/login" :loading="loading" large>Fazer login</v-btn>
              <v-spacer />
              <v-btn color="primary" type="submit" :loading="loading" large>Cadastrar</v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import { RepositoryFactory } from "@/repositories/RepositoryFactory";
const usuarioRepo = RepositoryFactory.get("usuario");

export default {
  props: ["to", "invalidToken"],
  data: () => ({
    valid: true,
    loading: false,
    name: "",
    email: "",
    password: "",
    habilitado: false,
    error: "",
    requiredRule: [v => !!v || "Campo obrigatório"],
    emailRules: [
      v => !!v || "Campo obrigatório",
      v => /.+@.+\..+/.test(v) || "E-mail deve ser válido"
    ]
  }),
  created() {
    if (this.invalidToken) {
      this.error = "Sua sessão expirou. Faça login novamente";
    }
  },
  methods: {
    onSubmit() {
      this.loading = true;

      if (this.$refs.form.validate()) {
        usuarioRepo
          .createUser({
            nome: this.nome,
            email: this.email,
            habilitado: this.habilitado,
            password: this.password
          })
          .then(res => {
            console.log(res.data);
          })
          .catch(console.error);
      }

      this.loading = false;
    }
  },
  computed: {
    ...mapState({
      token: state => state.auth.token
    })
  }
};
</script>

<style scoped lang="css">
</style>
