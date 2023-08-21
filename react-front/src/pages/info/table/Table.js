import React, {useMemo} from 'react';
import {useTable} from 'react-table';
import {COLUMNS} from '../columns';
import {COLUMNS2} from '../columns2';
import './Table.css'
import './Table2.css'
function Table(props) {
    let cl;
    let tablecss;
    if (props.name === 1){
        cl = COLUMNS;
        tablecss='infoTable2';
    }
    else{
        cl = COLUMNS2;
        tablecss='infoTable';
    }

    const columns = useMemo(() => cl, []);
    const data = useMemo(() => props.data, [props.data]);

    const tableInstance = useTable({
        columns,
        data
    })

    const {
        getTableProps, 
        getTableBodyProps, 
        headerGroups, 
        rows, 
        prepareRow,
    } = tableInstance;

    return (
        <table className={tablecss} {...getTableProps()}>
            <thead>
                {
                    headerGroups.map((headerGroup) => (
                        <tr className='header' {...headerGroup.getHeaderGroupProps()}>
                            {
                                headerGroup.headers.map((column) => (
                                    <th {...column.getHeaderProps()}>
                                        {column.render('Header')}
                                    </th>
                                ))
                            }
                        </tr>                        
                    ))
                }
            </thead>
            <tbody {...getTableBodyProps()}>
                {
                    rows.map(row => {
                        prepareRow(row);
                        return (
                            <tr className='list' {...row.getRowProps()}>
                                {
                                    row.cells.map((cell) => {
                                        return (
                                            <td {...cell.getCellProps()}>
                                                {cell.render('Cell')}
                                            </td>                                            
                                        )
                                    })
                                }
                            </tr>                            
                        )
                    })
                }

            </tbody>
        </table>
    );
}

export default Table;