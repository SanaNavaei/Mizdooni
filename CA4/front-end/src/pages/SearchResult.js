import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import PageLayout from 'components/PageLayout';
import Cards from 'components/Cards';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';

function SearchResult() {
  const [restaurants, setRestaurants] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const topText = 'Results for #' + searchParams;

  useEffect(() => {
    document.title = 'Search Result';
  }, []);

  useEffect(() => {
    fetch(`/api/restaurants?${searchParams}&page=1`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch restaurants');
        }
        return response.json();
      })
      .then(data => {
        setRestaurants(data.data.pageList);
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      }
      );
  }, []);


  return (
    <PageLayout>
      <Cards topText={topText} restaurants={restaurants} />
    </PageLayout>
  );
}

export default SearchResult;
