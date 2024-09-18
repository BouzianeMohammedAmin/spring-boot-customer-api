import {useEffect , useState} from 'react'
import './App.css'
import {Spinner, Stack, Wrap, WrapItem} from '@chakra-ui/react'
import SidebarWithHeader from './components/shared/SideBar'
import {getCostumers} from "./services/Client.jsx";
import CardWithImage from "./components/CardWithImage.jsx";
import CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";


function App() {
    const [customers , setCustomers]=useState([]);
    const [loading , setLoading] = useState(false) ;
const fetchCustomers = ()=>{
    setLoading(true) ;

    getCostumers().then(res=>{
        setCustomers(res.data) ;

    }).catch(err=>{
        console.log(err)
    }).finally(()=>{
        setLoading(false) ;
    })

}
    useEffect(()=>{
        fetchCustomers() ;

    },[])

    if(loading){
       return (
           <SidebarWithHeader>
               <Spinner
                   thickness='4px'
                   speed='0.65s'
                   emptyColor='gray.200'
                   color='blue.500'
                   size='xl'
               />
           </SidebarWithHeader>
       );
    }

    if(customers.length<=0){
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer fetchCustomers = {fetchCustomers}/>
                <Stack mt={5}><h1>No customers Loads </h1></Stack>
            </SidebarWithHeader>
        );
    }

  return (
        <SidebarWithHeader>
            <CreateCustomerDrawer fetchCustomers = {fetchCustomers}/>
            <Wrap justify={"center"} spacing="30" >

            {customers.map((customer , index) => (
                <WrapItem key={index}>
                    <CardWithImage
                        {...customer}
                    fetchCustomers = {fetchCustomers}
                        image_number={index}
                    />
                </WrapItem>
            ))}
            </Wrap>
        </SidebarWithHeader>

  )
}

export default App
