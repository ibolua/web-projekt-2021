<template>
  <div class="container">
    <!-- Button zum Hinzufügen des nächsten Bildes -->
    <button @click="geklickt()" class="button">
      <i class="fas fa-camera" />
    </button>
    <!-- Eingabefeld für inkrementelle Suche -->
    <section class="section">
      <input type="text" v-model="suchfeld" class="input" placeholder="Suche" />
    </section>
    <section class="section">
      <div class="columns is-multiline">
        <!-- Hier alle Bilder mit Hilfe der FotoGalerieBild-Komponente anzeigen -->
        <!-- flexibel natürlich - nicht die fünf Beispielbilder hardcoden! -->
        <FotoGalerieBild @delete-foto="deleteFoto" :foto="ele" v-for="(ele, i) in fotoitems" v-bind:key="i"/>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive, ref, Ref } from "vue";
import FotoGalerieBild from "./FotoGalerieBild.vue";
import { Foto } from "@/services/Foto";
import { fotoliste } from "@/services/FotoListe";

export default defineComponent({
  name: "FotoGalerie",
  
  components: {
    FotoGalerieBild
  },

  setup() {
    // var fotos: Foto[] = reactive([]);
    const fotos: Ref<Foto[]> = ref([]);
    let index = 0;

    const suchfeld = ref("");
    // const items: Ref<Foto[]> = ref([]);

    function geklickt() {
      if(index < fotoliste.length) {
        fotos.value.push(fotoliste[index]);
        index += 1;
        console.log("ich war hier");
      } else {
        alert("Keine Fotos mehr");
      }
    }

    const fotoitems = computed(() => {
      const n: number = suchfeld.value.length;
      if (suchfeld.value.length < 3) {
        return fotos.value;
      } else {
        // console.log(fotos.value[0].ort.toLowerCase());
        // console.log(suchfeld.value.toLowerCase());

        // console.log(fotos.value[0].ort.toLowerCase().includes(suchfeld.value.toLowerCase()));
        // return fotos.value.filter((e: { ort: string; }) => 
        //   e.ort.toLowerCase().includes(suchfeld.value.toLowerCase())
        // );

        var tmp = []
        for (let f of fotos.value) {
          console.log(f.ort);
          if(f.ort.toLowerCase().includes(suchfeld.value.toLowerCase())) {
            tmp.push(f);
          }
        }
        return tmp;


      }
    })


    function deleteFoto(id: number): void {
      console.log("deleteFotoo")
      fotos.value = fotoitems.value.filter(ele => ele.id !== id);

      // Irgendwie funktioniert das nicht so ganz.
      // Kann danach nicht nochmal 5 Bilder hinzufügen
      index -= 1;
    }


    return {
      geklickt, index, suchfeld, fotoitems, deleteFoto
    }
  }


  
});
</script>


<style scoped>

</style>
