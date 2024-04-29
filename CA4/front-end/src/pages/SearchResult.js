import Header from '../components/Header';
import Cards from '../components/Cards';
import Footer from '../components/Footer';
import TheIvyBrasserie from '../assets/images/restaurants/The-Ivy-Brasserie-1.png';
import HaliaRestaurant from '../assets/images/restaurants/Halia-Restaurant.png';
import ElineCafe from '../assets/images/restaurants/Eline-Cafe.png';
import Cecconis from '../assets/images/restaurants/Cecconis.png';
import HardRockCafe from '../assets/images/restaurants/Hard-Rock-Cafe.png';
import TwentyEightFiftyMarylebone from '../assets/images/restaurants/28-50-Marylebone.png';
import MamanJoonFelafel from '../assets/images/restaurants/Maman-Joon-Felafel.png';
import DizyAliDaei from '../assets/images/restaurants/Dizy-Ali-Daei.png';
import Orkideh from '../assets/images/restaurants/Orkideh.png';
import Narenjak from '../assets/images/restaurants/Narenjak.png';
import SIRAZKADIKOY from '../assets/images/restaurants/SIRAZ-KADIKOY.png';
import TheIvyBrasserie2 from '../assets/images/restaurants/The-Ivy-Brasserie-2.png';

function SearchResult() {
  const restaurants = [
    {
      name: 'The Ivy Brasserie',
      reviews: 2096,
      type: 'Contemporary British',
      location: 'Tehran',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: TheIvyBrasserie
    },
    {
      name: 'Halia Restaurant',
      reviews: 12096,
      type: 'Fast Food',
      location: 'Karaj',
      isOpen: false,
      startingTime: '10 AM',
      starCount: 5,
      image: HaliaRestaurant
    },
    {
      name: 'Eline Cafe',
      reviews: 0,
      type: 'Sea Food',
      location: 'Rasht',
      isOpen: true,
      closingTime: '12 PM',
      starCount: 5,
      image: ElineCafe
    },
    {
      name: 'Cecconi’s',
      reviews: 2096,
      type: 'Healthy Food',
      location: 'Ahvaz',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: Cecconis
    },
    {
      name: 'Hard Rock Cafe',
      reviews: 2096,
      type: 'Vegetarian Food',
      location: 'Tabriz',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: HardRockCafe
    },
    {
      name: '28 - 50 Marylebone',
      reviews: 2096,
      type: 'Kebab',
      location: 'Tehran',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: TwentyEightFiftyMarylebone
    },
    {
      name: 'Maman Joon Felafel',
      reviews: 10000000,
      type: 'Felafel',
      location: 'Qom',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: MamanJoonFelafel
    },
    {
      name: 'Dizy Ali Daei',
      reviews: 2096,
      type: 'Dizy',
      location: 'Boshehr',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: DizyAliDaei
    },
    {
      name: 'Orkideh',
      reviews: 2096,
      type: 'Iranian Food',
      location: 'Sari',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: Orkideh
    },
    {
      name: 'Narenjak',
      reviews: 2096,
      type: 'Local Food',
      location: 'Mashhad',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: Narenjak
    },
    {
      name: 'SIRAZ KADIKOY',
      reviews: 2096,
      type: 'Turkis Food',
      location: 'Tehran',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: SIRAZKADIKOY
    },
    {
      name: 'The Ivy Brasserie',
      reviews: 2096,
      type: 'Contemporary British',
      location: 'Tehran',
      isOpen: true,
      closingTime: '11 PM',
      starCount: 5,
      image: TheIvyBrasserie2
    
    }
  ];
  const restaurant_name = "Restaurant Name";
  return (
    <div className="min-vh-100 d-flex flex-column">
      <Header button="Reserve Now!" />

      <main className="flex-grow-1">
        <Cards restaurant_name={restaurant_name} restaurants={restaurants} />
      </main>

      <Footer />
    </div>
  );
}

export default SearchResult;
