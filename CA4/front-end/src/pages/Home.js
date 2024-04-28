import Header from '../components/Header';
import HomeSearch from '../components/HomeSearch';
import Cards from '../components/Cards';
import About from '../components/About';
import Footer from '../components/Footer';
import TheIvyBrasserie from '../assets/images/restaurants/The-Ivy-Brasserie-1.png';
import HaliaRestaurant from '../assets/images/restaurants/Halia-Restaurant.png';
import ElineCafe from '../assets/images/restaurants/Eline-Cafe.png';
import Cecconis from '../assets/images/restaurants/Cecconis.png';
import HardRockCafe from '../assets/images/restaurants/Hard-Rock-Cafe.png';
import TwentyEightFiftyMarylebone from '../assets/images/restaurants/28-50-Marylebone.png';

import '../assets/stylesheets/cards.css';
import '../assets/stylesheets/home.css';

function Home() {
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
    }
  ];
  return (
    <div class="min-vh-100 d-flex flex-column bg-white">
      <Header button="Reserve Now!" />

      <main class="flex-grow-1">
        <HomeSearch />
        <Cards description="Top Restaurants in Mizdooni" restaurants={restaurants} />
        <About />
      </main>
    
      <Footer />
    </div>
  );
}

export default Home;
