
import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import React from "react";
import CreateCustomForm from "./CreateCustomForm.jsx";
import { IoIosPersonAdd } from "react-icons/io";
import { AiOutlineClose } from "react-icons/ai";





const CreateCustomerDrawer = ({fetchCustomers})=>{
    const { isOpen, onOpen, onClose } = useDisclosure()
    const btnRef = React.useRef()

    return(
        <>

        <Button
            leftIcon={<IoIosPersonAdd/>}
            colorScheme={"teal"}
            onClick={onOpen} >
                create New User </Button>

            <Drawer
                isOpen={isOpen}
                placement='right'
                onClose={onClose}
                finalFocusRef={btnRef}
                size={"xl"}m
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader >Create New  Customer</DrawerHeader>

                    <DrawerBody>
                        <CreateCustomForm fetchCustomers = {fetchCustomers}/>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            leftIcon={<AiOutlineClose/>}
                            onClick={onClose} >
                           Close </Button>

                    </DrawerFooter>
                </DrawerContent>
            </Drawer>

        </>
    ) ;
};

export  default  CreateCustomerDrawer ;