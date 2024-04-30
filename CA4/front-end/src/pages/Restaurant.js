import PageLayout from 'components/PageLayout';
import RestaurantDetails from 'components/RestaurantDetails';
import Reserve from 'components/Reserve';
import RestaurantReviews from 'components/RestaurantReviews';

import DizyAliDaei from 'assets/images/restaurants/Dizy-Ali-Daei.png';

import 'assets/stylesheets/restaurant.css';

const restaurant = {
  name: 'Dizy Ali Daei',
  maxSeatNumber: 10,
  reviews: 160,
  type: 'Dizy',
  country: 'Iran',
  city: 'Boshehr',
  street: 'Vali-e-asr Square',
  isOpen: true,
  closingTime: '10 PM',
  startingTime: '11 AM',
  starCount: 4,
  foodRate: 4.5,
  serviceRate: 4.1,
  ambienceRate: 3.8,
  overallRate: 4,
  image: DizyAliDaei,
  availableTimes: [
    { date: '2024-02-18', time: '11:00 PM' },
    { date: '2024-02-18', time: '12:00 PM' },
    { date: '2024-02-18', time: '13:00 PM' },
    { date: '2024-02-18', time: '14:00 PM' },
    { date: '2024-02-18', time: '15:00 PM' },
    { date: '2024-02-18', time: '18:00 PM' },
    { date: '2024-02-18', time: '19:00 PM' },
    { date: '2024-02-18', time: '20:00 PM' },
  ],
  description: `Ali Daei Dizy restaurant is a cultural oasis in the heart of the city, serving up the best of traditional Iranian cuisine.
  With a menu that boasts a diverse selection of flavorful dishes such as kebabs, stews, and rice dishes,
  guests will experience the richness and depth of Persian flavors.
  The ambiance of the restaurant is warm and inviting,
  with intricate Persian rugs adorning the walls and the soothing sounds of traditional Iranian music playing in the background.
  Whether you're looking to indulge in a delicious meal with friends or simply craving a taste of Iran,
  Ali Daei Dizy restaurant is the perfect spot to satisfy your culinary cravings.`
};

const reviews = [
  {
    reviewerName: 'Ali Daei',
    foodRate: 5,
    serviceRate: 5,
    ambianceRate: 5,
    overallRate: 5,
    date: 'February 17, 2024',
    comment: 'Excellent pre-theatre meal. Good food and service. Only small criticism is that music was intrusive.',
    starCount: 5
  },
  {
    reviewerName: 'Ali Dari',
    foodRate: 4,
    serviceRate: 4,
    ambianceRate: 4,
    overallRate: 4,
    date: 'February 17, 2024',
    comment: 'Excellent pre-theatre meal. Good food and service. Only small criticism is that music was intrusive.',
    starCount: 4
  },
  {
    reviewerName: 'Ali Daryaei',
    foodRate: 5,
    serviceRate: 5,
    ambianceRate: 5,
    overallRate: 5,
    date: 'February 17, 2024',
    comment: 'Excellent pre-theatre meal. Good food and service. Only small criticism is that music was intrusive.',
    starCount: 5
  },
  {
    reviewerName: 'Ali Daryaei',
    foodRate: 5,
    serviceRate: 5,
    ambianceRate: 5,
    overallRate: 5,
    date: 'February 17, 2024',
    comment: 'Excellent pre-theatre meal. Good food and service. Only small criticism is that music was intrusive.',
    starCount: 5
  },
  {
    reviewerName: 'Ali Daryaei',
    foodRate: 5,
    serviceRate: 5,
    ambianceRate: 5,
    overallRate: 5,
    date: 'February 17, 2024',
    comment: 'Excellent pre-theatre meal. Good food and service. Only small criticism is that music was intrusive.',
    starCount: 5
  }
];

function Restaurant() {
  return (
    <PageLayout>
      <div className="container pt-5">
        <div className="row gap-4">
          <RestaurantDetails {...restaurant} />
          <Reserve {...restaurant} />
          <RestaurantReviews {...{ restaurant, reviews }} />
        </div>
      </div>
    </PageLayout>
  );
}

export default Restaurant;
