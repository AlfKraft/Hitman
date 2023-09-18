<template>
  <v-container class="justify-center bg-transparent">
    <v-row>
      <v-col class="homeView rounded">
        <v-card v-if="!languageSwitch" class="bg-black">
          <v-row class="justify-center mt-4">
            <h1 class="text-white">HITMA<span class="text-red">N</span></h1>
          </v-row>
          <v-row class="justify-center">
            <v-card-actions>
              <v-btn @click="languageSwitch = !languageSwitch">English</v-btn>
            </v-card-actions>
          </v-row>
          <v-card-text>
            Reeglid:
          </v-card-text>
          <v-card-text>Mängu eesmärgiks on kõrvaldada kõik oma sihtmärgid ja jääda ise ellu. Mäng koosneb kolmest põhiosast, mille eest saab võidupunkte: sihtmärkide kõrvaldamine, salamissioonide sooritamine ja kontrollpunktide läbimine. Võitja on osaleja, kes on mängu lõpuks kogunud kõige suurema punktisumma.</v-card-text>
          <v-card-text>Mängu alguses ilmub veebikeskkonda sihtmärk, mida võistleja näeb sisselogituna. Kõrvaldatud sihtmärgilt saab mängija tema personaalse koodi, mis tuleb sisestada veebisüsteemi, et saada uus sihtmärk. Sihtmärgid vahetuvad igal öösel kell üks automaatselt. Sihtmärkide elimineerimine toimub ajavahemikus 01:00-23:59. NB! Kellaaegadel 00:00-00:59 toimub sihtmärkide vahetus, sellel ajal tapmist toimuda ei saa.</v-card-text>
          <v-card-text>Kõrvaldada saab vaid endale määratud sihtmärki ning mitte ühtki teist mängijat. Sihtmärgi kõrvaldamine on keelatud olukorras, kus see võib põhjustada kahju osaleja tervisele või varale.</v-card-text>
          <v-card-text>Sihtmärgi kõrvaldamiseks on lubatud kasutada ainult ühte kahest vahendist: lusikat või NERF püstolit. Kõik muud vahendid on keelatud ning sihtmärgi kõrvaldamisel ei loe. Sihtmärk loetakse kõrvaldatuks, kui teda puudutatakse lusikaga või tabatakse NERF püstolist lastud pehme nerf moonaga. Pärast kõrvaldamist tuleb sihtmärgilt küsida tema isklik kood ning sisestada see veebilehele www.hitman.ee. Sihtmärgi eemaldamine annab 100 võidupunkti.  Vaidluste ennetamiseks on soovitav teha igast sihtmärgi kõrvaldamisest video tõestus. Kui tuvastatakse olukord, kus osaleja ei korja enda järelt kokku NERF püstolist lastud kuule, kaotab osaleja punkte.</v-card-text>
          <v-card-text>Mängu jooksul toimuvad erinevad lisamissioonid, mille kohta saavad mängijad infot veebilehele sisse logides vajutades valikmenüüst ‘’missions’’ lahtrile. Missiooni juures on välja toodud täpne ülesanne koos tähtaja ja asukohaga, v.a missioonidel, kus asukoht tuleb vihjete põhjal välja mõelda. Olukorras, kus tuvastatakse, et mängija on missiooni läbimiseks kasutanud ebaausaid võtteid, diskvalifitseeritakse osaleja automaatselt võimaluseta mänguga uuesti liituda.</v-card-text>
          <v-card-text>Korra päevas tuleb läbida Tartusse paigutatud kontrollpunkt, mis tuleb sisestada veebilehel, et püsida mängus. Kell 23:59 vahetub kontrollpunkti kood ning asukoht. Kontrollpunkti läbimiseks on aeg 01:00-23:59. Kui kontrollpunkt jääb läbimata, siis elimineeritakse mängija südaöösel.</v-card-text>
          <v-card-text>Võistluse 5 kõige rohkem punkte teeninud mängijate (TOP 5) nimed on avalikud kõikidele mängijatele, mis läheb lukku iga päeva lõpus kell 23:59. Juhul, kui oled mängust kõrvaldatud, siis mängu tagasi saamiseks on võimalik mängu reegleid järgides kõrvaldada üks TOP 5 mängijatest. Neid kutsutakse "Bounty missions", mis on kätte saadavd "MISSIONS" lehel iga päev kell 01:00. TOP 5 missiooni sooritanud agent saab tagasi mängu järgmise sihtmärkide vahetuse ajal. TOP 5 mängija, kellele oli pandud bounty, kaotab 150 punkti. Elimineeritud TOP 5 mängija on jätkuvalt mängus.</v-card-text>
          <v-card-text>Võistlusel on eriauhind sihtmärkide eemaldamise filmimise eest. Osalemiseks tuleb videojäädvustus saata korraldajatele meilile. Võistlusel osalemiseks peab olema filmitava isiku nõusolek.</v-card-text>
          <v-card-text>Võistluse võitjad ja info auhindade kättesaamise kohta kuulutatakse välja Facebookis (ja/või ka meili teel) võistluse ürituse all hiljemalt Tudengipäevade festivali viimasel päeval (Auhinna saavad: Top score, Viimane elus, Parim elimineerimine).</v-card-text>
          <v-card-text>Mängu raames on keelatud põhjustada varalist või füüsilist kahju teistele mängijatele. Tartu Tudengipäevad ei võta endale vastutust mängijate poolt põhjustatud õnnetuste eest. Osaleja on kohustatud järgima kõiki Eesti Vabariigi seadusi. Võistlusele registreerumisega kinnitab osaleja, et ta on nõus kõigi võistluse reeglitega ning oma andmete jagamisega võistluse raames. Olles tunnistajaks mistahes reeglite rikkumisele, on osaleja kohustatud korraldajaid sellest informeerima. Lisaks tuleb korraldajaid informeerida mistahes muu mänguga seotud probleemi korral või soovi korral mängust lahkuda.</v-card-text>
          <v-card-text>Ürituse korraldajad ei võta vastutust ürituse ajal tekkinud osalejate vigastuste või tervisekahjustuste eest. Iga mängus osaleja vastutab ise enda turvalisuse ja tervise hoidmise eest. Võistlusele registreerumisega kinnitab osaleja, et on nõus kõigi võistluse reeglitega ning järgib võistluse jooksul korraldajatelt saadud juhiseid.</v-card-text>
          <v-card-text>Võistluse hea tava:</v-card-text>
          <v-card-text>Osale Tartu Tudengipäevade üritustel, viibi võimalikult palju väljas, võta missioonidest osa. Juhul kui sind kõrvaldati, käitu viisakalt ning aktsepteeri olukorda. Ära võta teistelt mängijatelt mängurõõmu. Austa teisi mängijaid, nende vara ja mainet. Edu!</v-card-text>
          <v-card-text>Probleemide korral võta ühendust: Wiljar Lobjakas (wiljar.lobjakas@gmail.com), Rasmus Rohtla (rasmusr16@hotmail.com) või Alfred Kraft (alfredkraftofficial@gmail.com).</v-card-text>
        </v-card>
        <v-card v-if="languageSwitch" class="bg-black">
          <v-row class="justify-center mt-4">
            <h1 class="text-white">HITMA<span class="text-red">N</span></h1>
          </v-row>
          <v-row class="justify-center">
            <v-card-actions>
              <v-btn @click="languageSwitch = !languageSwitch">Estonian</v-btn>
            </v-card-actions>
          </v-row>
          <v-card-text>
            Rules:
          </v-card-text>
          <v-card-text>The objective of the game is to eliminate all of your targets and survive. The game consists of three main parts, for which you can earn victory points: eliminating targets, completing secret missions, and passing through checkpoints. The winner is the participant who has accumulated the highest score by the end of the game.</v-card-text>
          <v-card-text>At the beginning of the game, a target will appear in the online environment that the participant can see when logged in. Once a target is eliminated, the player receives their unique personal code, which must be entered into the website to receive a new target. Targets change automatically every night at 01:00. Target elimination takes place between 01:00 and 23:59. Please note that between 00:00 and 00:59, target changes occur, and no eliminations are allowed during that time.</v-card-text>
          <v-card-text>Participants can only eliminate their assigned targets and no other players unless the organisers grant special permission during the game. Target elimination is prohibited if it could cause harm to a participant's health or property.</v-card-text>
          <v-card-text>To eliminate a target, participants are allowed to use only one of two tools: a spoon or a NERF pistol. All other tools are prohibited and do not count for target elimination. A target is considered eliminated when touched with a spoon or hit by a NERF pistol bullet. After elimination, the player must request the target's personal code and enter it on the website www.hitman.ee. Eliminating a target earns 100 victory points. To prevent disputes, it is advisable to make a video recording of each target elimination. If a situation arises where a participant fails to collect NERF pistol bullets they fired, they will lose points.</v-card-text>
          <v-card-text>Throughout the game, various bonus missions take place, and players can get information by logging into the website and clicking on the "missions" tab in the menu. Each mission includes a specific task, deadline, and location, except for missions where the location must be deduced from clues. If it is discovered that a player has used unfair methods to complete a mission, they will be disqualified and will be prohibited from rejoining the game.</v-card-text>
          <v-card-text>Once a day, participants must pass through a checkpoint placed in Tartu, which must be entered on the website to stay in the game. The checkpoint code and location change at 23:59. Passing through the checkpoint is allowed between 01:00 and 23:59. If a control point is not passed, the player will be eliminated at midnight.</v-card-text>
          <v-card-text>The names of the TOP 5 players who have earned the most points in the competition will be made public to all players. If you have been eliminated from the game, you get a chance to return to the game by hunting down one of the TOP 5 players. These are called "Bounty missions" and they are available every day after 01:00. The agent who completes one of the TOP 5 bounty missions will rejoin the game during the next target change. A TOP 5 player who is eliminated loses 150 points. Eliminated TOP 5 players continue to participate in the game.</v-card-text>
          <v-card-text>There is a special prize for filming the elimination of targets. To participate an email of the video footage must be sent to the organisers. Participants must have the consent of the person being filmed. The winners of the competition and information about prize collection will be announced on Facebook (and/or by email) under the event of the competition no later than the last day of the Student Days festival (Prizes awarded: Top Score, Last Alive, Best Elimination).</v-card-text>
          <v-card-text>During the game, it is prohibited to cause financial or physical harm to other players. Tartu Student Days do not take responsibility for accidents caused by players. Participants are required to follow all laws of the Republic of Estonia. By registering for the competition, participants confirm that they agree to all competition rules and the sharing of their data within the context of the competition. If you witness any rule violations or encounter any other issues related to the game, you are obligated to inform the organisers. Additionally, you should contact the organisers in case of any problems or if you wish to leave the game.</v-card-text>
          <v-card-text>The event organisers do not take responsibility for injuries or health damage that participants may incur during the event. Each participant is responsible for their own safety and health. By registering for the competition, participants confirm that they agree to all competition rules and will follow the instructions given by the organisers during the competition.</v-card-text>
          <v-card-text>Rules of Good Conduct in the Competition: Participate in Tartu Student Days events, spend as much time outside as possible, and take part in missions. If you are eliminated, behave courteously and accept the situation. Do not steal the enjoyment of the game from other players. Respect other players, their property, and reputation. Good luck!</v-card-text>
          <v-card-text>In case of problems, contact: Wiljar Lobjakas (wiljar.lobjakas@gmail.com), Rasmus Rohtla (rasmusr16@hotmail.com), or Alfred Kraft (alfredkraftofficial@gmail.com).</v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import Start from "@/components/Start"
import {ref} from "vue";
const languageSwitch = ref(true)
</script>

<style scoped lang="scss">

.homeView {
  border: thin solid darkred;
}

</style>
