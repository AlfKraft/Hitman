<template>
  <v-container class="justify-center bg-transparent">
    <v-row>
      <v-col class="homeView rounded">
        <v-card class="bg-black">
          <v-row class="justify-center mt-4">
            <h1 class="text-white">HITMA<span class="text-red">N</span></h1>
          </v-row>
          <v-card-text>
            Rules:
          </v-card-text>
          <v-card-text>Mängu eesmärgiks on kõrvaldada kõik oma sihtmärgid ja jääda ise ellu. Mäng koosneb kolmest põhiosast, mille eest saab võidupunkte: sihtmärkide kõrvaldamine, salamissioonide sooritamine ja kontrollpunktide läbimine. Võitja on osaleja, kes on mängu lõpuks kogunud kõige suurema punktisumma.</v-card-text>
          <v-card-text>Mängu alguses ilmub veebikeskkonda sihtmärk, mida võistleja näeb sisselogituna. Kõrvaldatud sihtmärgilt saab mängija tema personaalse koodi, mis tuleb sisestada veebisüsteemi, et saada uus sihtmärk. Sihtmärgid vahetuvad igal öösel kell üks automaatselt. Sihtmärkide elimineerimine toimub ajavahemikus 01:00-23:59. NB! Kella aegadel 00:00-01:00 toimub sihtmärkide vahetus, sellel kella ajal tapmist toimuda ei saa.</v-card-text>
          <v-card-text>Kõrvaldada saab vaid endale määratud sihtmärki ning mitte ühtki teist mängijat, kui just korraldajad selleks mängu käigus eriluba ei anna. Sihtmärgi kõrvaldamine on keelatud olukorras, kus see võib põhjustada kahju osaleja tervisele või varale.</v-card-text>
          <v-card-text>Sihtmärgi kõrvaldamiseks on lubatud kasutada ainult ühte kahest vahendist: lusikat või NERF püstolit. Kõik muud vahendid on keelatud ning sihtmärgi kõrvaldamisel ei loe. Sihtmärk loetakse kõrvaldatuks, kui teda puudutatakse lusikaga või tabatakse NERF püstolist. Pärast kõrvaldamist tuleb sihtmärgilt küsida tema isklik kood ning sisestada see veebilehele www.hitman.ee. Sihtmärgi eemaldamine annab 100 võidupunkti.  Vaidluste ennetamiseks on soovitav teha igast sihtmärgi kõrvaldamisest video tõestus. Kui tuvastatakse olukord, kus osaleja ei korja enda järelt kokku NERF püstolist lastud kuule, kaotab osaleja 10% oma punktidest.</v-card-text>
          <v-card-text>Mängu jooksul toimuvad erinevad lisa missioonid, mille kohta saavad mängijad infot veebilehele sisse logides. Missiooni juures on välja toodud täpne ülesanne koos tähtaja ja asukohaga, v.a missioonidel, kus asukoht tuleb vihjete põhjal välja mõelda. Missioonide pidev täitmine on oluline, sest teatud ülesanded on avalikud vaid eelmiste missioonide täitjatele. Missioonidel on osalejatel unikaalsed kontrollkoodid, mis tähendab, et iga osaleja peab ise ülesande sooritama. Olukorras, kus tuvastatakse, et mängija on missiooni läbimiseks kasutanud ebaausaid võtteid, diskvalifitseeritakse osaleja automaatselt võimaluseta mänguga uuesti liituda. </v-card-text>
          <v-card-text>Korra päevas tuleb läbida Tartusse paigutatud kontrollpunkt, mis tuleb sisestada, et püsida mängus. Kell 23:59 vahetub kontrollpunkti kood ning asukoht. Kontrollpunkti läbimiseks on aeg 01:00-23:59. Esimesed 10 kontrollpunkti läbijat saavad 50 punkti. </v-card-text>
          <v-card-text>Võistluse 5 kõige rohkem punkte teeninud mängijate (Top5) nimed on avalikud kõikidele mängijatele, mis läheb lukku iga päeva lõpus kell 23:59. Kõrvaldatud agendid peavad mängu tagasi saamiseks mängureegleid järgides kõrvaldama ühe Top5 mängijatest ning esitama tema personaalse koodi veebilehe kaudu. Top5 mängija elimineerides saab kõrvaldatud agent tagasi mängu ja 100 punkti. Top5 mängija, keda elimineeriti, kaotab 100 punkti. Elimineeritud Top5 mängija ei ole jäädavalt mängust väljas, ta lisatakse tagasi mängu järgneval päeval kell 01:00.</v-card-text>
          <v-card-text>Võistlusel on eriauhind sihtmärkide eemaldamise filmimise eest. Osalemiseks tuleb videojäädvustus saata meiliaadressil info@studentdays.ee. Võistlusel osalemiseks peab olema filmitava isiku nõusolek.</v-card-text>
          <v-card-text>Võistluse kulminatsioon on 30.09 õhtul ning selleks ajaks elusoleva staatusega osalejad peavad viibima Tartus. Võistluse võitjad ja info auhindade kättesaamise kohta kuulutatakse välja Facebookis (ja/või ka meili teel) võistluse ürituse all hiljemalt 1. oktoobril (Auhinna saavad: Top score, Viimane elus, Parim elimineerimine).</v-card-text>
          <v-card-text>Mängu raames on keelatud põhjustada varalist või füüsilist kahju teistele mängijatele. Tartu Tudengipäevad ei võta endale vastutust mängijate poolt põhjustatud õnnetuste eest. Osaleja on kohustatud järgima kõiki Eesti Vabariigi seadusi. Võistlusele registreerumisega kinnitab osaleja, et ta on nõus kõigi võistluse reeglitega ning oma andmete jagamisega võistluse raames. Olles tunnistajaks mistahes reeglite rikkumisele, on osaleja kohustatud korraldajaid sellest informeerima. Lisaks tuleb korraldajaid informeerida mistahes muu mänguga seotud probleemi korral või soovi korral mängust lahkuda.</v-card-text>
          <v-card-text>Ürituse korraldajad ei võta vastutust ürituse ajal tekkinud osalejate vigastuste või tervisekahjustuste eest. Iga registreerunu ning mängus osaleja vastutab ise enda turvalisuse ja tervise hoidmise eest. Võistlusele registreerumisega kinnitab osaleja, et on nõus kõigi võistluse reeglitega ning järgib võistluse jooksul korraldajatelt saadud juhiseid.</v-card-text>
          <v-card-text>Võistluse hea tava:</v-card-text>
          <v-card-text>Osale Tartu Tudengipäevade üritustel, viibi võimalikult palju väljas, võta missioonidest osa. Juhul kui sind kõrvaldati, käitu viisakalt ning aktsepteeri olukorda. Ära võta teistelt mängijatelt mängurõõmu. Austa teisi mängijaid, nende vara ja mainet. Probleemide esinemisel suhtle korraldajatega. Pane hullu!</v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Start from "@/components/Start"
</script>

<style scoped lang="scss">

.homeView {
  border: thin solid darkred;
}

</style>
