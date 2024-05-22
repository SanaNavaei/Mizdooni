import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import PageLayout from 'components/PageLayout';
import Cards from 'components/Cards';
import Pagination from 'components/Pagination';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';

function SearchResult() {
  useEffect(() => { document.title = 'Search Result'; }, []);

  const [restaurants, setRestaurants] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const topText = 'Results for #' + searchParams;

  useEffect(() => {
    fetch(`/api/restaurants?${searchParams}&page=${currentPage}`, {
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
        setTotalPages(data.data.totalPages);
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      }
      );
  }, [searchParams, currentPage]);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <PageLayout>
      <Cards topText={topText} restaurants={restaurants} />
      <Pagination totalPages={totalPages} currentPage={currentPage} onPageChange={handlePageChange} />
    </PageLayout>
  );
}

export default SearchResult;
