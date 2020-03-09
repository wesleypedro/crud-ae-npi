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
                  <v-card>
                    <v-card-title>
                      <span class="headline">Editar item</span>
                    </v-card-title>

                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.nome" label="Nome"></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.email" label="Email"></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <label>Ativo</label>
                            <v-simple-checkbox v-model="editedItem.habilitado"></v-simple-checkbox>
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

export default {
  name: "home",
  components: {
    VCardWidget
  },

  data: () => ({
    dialog: false,
    headers: [
      {
        text: "Nome",
        align: "left",
        value: "nome"
      },
      { text: "Email", value: "email" },
      { text: "Ativo", value: "habilitado" },
      { text: "Ação", value: "action" }
    ],
    usuarios: [],
    editedIndex: -1,
    editedItem: {
      id: -1,
      nome: "",
      email: "",
      habilitado: false,
      password: "",
    }
  }),

  created() {
    usuarioRepo
      .getAll()
      .then(res => {
        this.usuarios = res.data;
        console.log(this.usuarios[0].password)
      })
      .catch(console.error);
  },

  computed: {},

  methods: {
    editItem(item) {
        this.editedIndex = this.usuarios.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;
    },

    deleteItem(item) {
      if(!item.habilitado){
        const index = this.usuarios.indexOf(item);
        if (confirm("Você tem certeza que deseja deletar esse item?")) {
          usuarioRepo
            .deleteUser(this.usuarios[index].id)
            .then(res => {
              this.usuarios = res.data;
            })
            .catch(console.error);
        }
      }else{
        alert("Apenas usuarios inativos podem ser deletados")
      }
    },
    close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = {
            name: "",
            email: "",
            habilitado: false,
            password: "",
          }
          this.editedIndex = -1
        }, 300)
      },

      save () {
        usuarioRepo.updateUser(this.editedItem.id, this.editedItem)
        .then(res => {
          if(res.data !== []){
            this.usuarios = res.data;
          }
        })
        .catch(console.error);
        this.close()
      }
  }
};
</script>
