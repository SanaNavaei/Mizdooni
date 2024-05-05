import { useState, useEffect } from 'react';

import AddTableModal from './AddTableModal';
import Hashtag from 'assets/icons/hashtag.svg';
import Seat from 'assets/icons/seat.svg';

function RestaurantTables({ restaurantId, setTableNumber }) {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);

  const reloadTables = () => {
    const fetchTables = async () => {
      try {
        const response = await fetch(`/api/tables/${restaurantId}`);
        if (response.ok) {
          const data = await response.json();
          setTables(data.data);
        } else {
          console.error('Failed to fetch tables');
        }
      } catch (error) {
        console.error('Error fetching tables:', error);
      }
    };

    fetchTables();
  }

  useEffect(() => {
    reloadTables();
  }, [restaurantId]);

  const noTableText = (
    <div className="container d-flex justify-content-center align-items-center flex-grow-1">
      <p>No Tables have been added.</p>
    </div>
  );

  let classContainer = "container pt-5 pb-3 px-5 ";
  if (tables.length === 0) {
    classContainer += "d-none";
  }

  const handleTable = (tableNumber) => {
    console.log(tableNumber);
    setTableNumber(tableNumber);
    setSelectedTable(tableNumber);
  };

  return (
    <section id="tables" className="col-lg-8 h-100 overflow-auto p-2 d-flex flex-column">
      <div className="d-flex justify-content-start mb-3">
        <button className="miz-text-red manage-button-link fs-6 fw-normal ps-2 m-0" data-bs-toggle="modal" data-bs-target="#addTable" >+Add Table</button>
      </div>
      <div className={classContainer}>
        <div className="row row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-xl-5 row-cols-xxl-6 g-4 text-white">
          {tables.map((table, index) => (
            <div className="col" key={index}>
              <button className="border-0" onClick={() => handleTable(table.tableNumber)}>
                <div className="table-card mx-auto rounded-3 d-flex flex-column justify-content-center gap-1" style={{ backgroundColor: selectedTable === table.tableNumber ? '#ED3545ed' : '' }}>
                  <div className="d-flex justify-content-evenly align-items-center">
                    <img src={Hashtag} alt="Number" width="19" height="19" />
                    <span className="text-white">{table.tableNumber}</span>
                  </div>
                  <div className="d-flex justify-content-evenly align-items-center">
                    <img src={Seat} alt="Seat" width="22" height="18" />
                    <span className="text-white">{table.seatsNumber}</span>
                  </div>
                </div>
              </button>
            </div>
          ))}
        </div>
      </div>
      {tables.length === 0 && noTableText}
      <AddTableModal restaurantId={restaurantId} reloadTables={reloadTables}/>
    </section >
  );
}

export default RestaurantTables;
