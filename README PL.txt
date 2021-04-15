Aplikacja to symulator poruszających się po świecie statków i samolotów. 

Po włączeniu programu ukazuje się nam okno mapy oraz okno kontrolne z lewej strony,
z którego możemy przechodzić do okien tworzenia obiektów na mapie. 

Tworząc statki wybieramy tylko ich port startowy, po czym zaczynają się one poruszać 
w losowych kierunkach. Przy tworzeniu samolotów musimy ustalić ich cyklicznie 
przebywaną trasę.

Każdy pojazd ma swój osobny wątek, który wprawia go w ruch. Jeśli w danej lokalizacji
spotka się zbyt dużo pojazdów, ustawiają się one w kolejce i czekają na możliwość
kontynuacji podróży. Na lotniskach jest to rozszerzone do podziału na samoloty
czekające na lądowanie oraz te stojące już na pasie startowym.

Po kliknięciu jednego z obiektów na mapie, otwiera się okno zawierające wszystkie
opisujące go informacje. Dotyczy to nie tylko pojazdów, ale też lokalizacji.

Okno informacyjne samolotów pozwala na zmianę ich cyklicznej trasy oraz na 
lądowanie awaryjne na najbliższym lotnisku. Wszystkie pojazdy mogą zostać usunięte
przez ich okna informacyjne. Okna lokalizacji pokazują nam listy pojazdów znajdujących
się aktualnie w kolejce.

Pojazdy dzielą się na wojskowe oraz pasażerskie. Pojazdy pasażerskie przy każdym
zatrzymaniu się w lokalizacji zabierają na pokład pewną ilość pasażerów.

Pojazdy wojskowe są wyposażone w typy uzbrojenia. Samoloty wojskowe mogą zacząć
podróż tylko ze statków wojskowych (lotniskowców), dlatego wpierw trzeba taki stworzyć.

Program napisany został przy pomocy IntelliJ, posługując się Scene Builderem oraz JavaFX 15.0.1