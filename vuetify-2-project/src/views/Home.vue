<template>
  <div>
    <v-card-widget enableActions :title="'Página Inicial'">
      <div slot="widget-header-action"></div>
      <div slot="widget-content">
        <v-row>
          <v-col cols="12">
            <v-data-table
              :headers="headers"
              :items="usuarios"
              :items-per-page="5"
              class="elevation-1"
            >
              <template v-slot:top>
                <v-dialog v-model="dialog" max-width="500px">
                  <template v-slot:activator="{ on }">
                    <v-btn color="primary" dark class="mb-2" v-on="on">Novo usuário</v-btn>
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="headline">{{formTitle}}</span>
                    </v-card-title>

                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="editedItem.nome"
                              label="Nome"
                              required
                              :rules="requiredRule"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="editedItem.email"
                              label="Email"
                              required
                              :rules="emailRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <label>Ativo</label>
                            <v-simple-checkbox v-model="editedItem.habilitado"></v-simple-checkbox>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-select
                              :items="cursoNomes"
                              v-model="editedItem.curso.nome"
                              label="Curso"
                            ></v-select>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.password" label="Senha"></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>

                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
                      <v-btn color="blue darken-1" text @click="save">Salvar</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </template>

              <template v-slot:item.habilitado="{ item }">
                <v-simple-checkbox v-model="item.habilitado" disabled></v-simple-checkbox>
              </template>

              <template v-slot:item.action="{ item }">
                <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
                <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
              </template>
            </v-data-table>
          </v-col>
        </v-row>
      </div>
    </v-card-widget>
  </div>
</template>

<script>
// @ is an alias to /src
import VCardWidget from "@/components/VWidget";
import { RepositoryFactory } from "@/repositories/RepositoryFactory";
const usuarioRepo = RepositoryFactory.get("usuario");
const cursoRepo = RepositoryFactory.get("curso");

export default {
  name: "home",
  components: {
    VCardWidget
  },

  data: () => ({
    requiredRule: [v => !!v || "Campo obrigatório"],
    emailRules: [
      v => !!v || "Campo obrigatório",
      v => /.+@.+\..+/.test(v) || "E-mail deve ser válido"
    ],
    dialog: false,
    headers: [
      {
        text: "Nome",
        align: "left",
        value: "nome"
      },
      { text: "Email", value: "email" },
      { text: "Ativo", value: "habilitado" },
      { text: "Curso", value: "curso.nome" },
      { text: "Ação", value: "action" }
    ],
    usuarios: [],
    cursos: [],
    cursoNomes: [],
    editedIndex: -1,
    editedItem: {
      id: -1,
      nome: "",
      email: "",
      habilitado: false,
      curso: {},
      password: ""
    }
  }),

  created() {
    usuarioRepo
      .getAll()
      .then(res => {
        this.usuarios = res.data;
        console.log(this.usuarios[0]);
      })
      .catch(console.error);
    cursoRepo
      .getAll()
      .then(res => {
        this.cursos = res.data;
        console.log(this.cursos[0].usuarios);
        this.cursos.forEach(curso => {
          this.cursoNomes.push(curso.nome);
        });
      })
      .catch(console.error);
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Novo usuário" : "Editar usuário";
    }
  },

  methods: {
    editItem(item) {
      this.editedIndex = this.usuarios.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.editedItem.password = ""
      this.dialog = true;
    },

    deleteItem(item) {
      if (!item.habilitado) {
        const index = this.usuarios.indexOf(item);
        if (confirm("Você tem certeza que deseja deletar esse item?")) {
          usuarioRepo
            .deleteUser(this.usuarios[index].id)
            .then(res => {
              this.usuarios = res.data;
            })
            .catch(console.error);
        }
      } else {
        alert("Apenas usuarios inativos podem ser deletados");
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = {
          id: -1,
          nome: "",
          email: "",
          habilitado: false,
          curso: {},
          password: ""
        };
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      var c = {}
      console.log(this.editedItem.curso.nome);
      this.cursos.forEach(curso => {
        if(curso.nome === this.editedItem.curso.nome ){
          c = curso;
        }
      });
      this.editedItem.curso = c;
      if (this.editedItem.id > -1) {
        usuarioRepo
          .updateUser(this.editedItem.id, this.editedItem)
          .then(res => {
            if (res.data !== []) {
              this.usuarios = res.data;
            }
          })
          .catch(console.error);
      } else {
        usuarioRepo
          .createUser(this.editedItem)
          .then(res => {
            if (res.data !== []) {
              this.usuarios = res.data;
            }
          })
          .catch(console.error);
      }
      this.close();
    }
  }
};
</script>
