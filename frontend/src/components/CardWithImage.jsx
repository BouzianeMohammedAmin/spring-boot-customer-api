import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Button,
    useColorModeValue, Tag,
    AlertDialog,
    AlertDialogBody,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogContent,
    AlertDialogOverlay,
    AlertDialogCloseButton, useDisclosure,
} from '@chakra-ui/react';
import {deleteCustomer} from "../services/Client.jsx";
import {useRef} from "react";
import {notification} from "../services/notification.js";
import CreateCustomerDrawer from "./CreateCustomerDrawer.jsx";
import UpdateCustomerDrawer from "./UpdateCustomerDrawer.jsx";

export default function CardWithImage({id , name , email , age , gender , image_number , fetchCustomers }) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()
    gender = (gender == "MALE") ? "men" : "women" ;
    const url_image = ' https://randomuser.me/api/portraits/med/' +gender+  '/' +image_number+ '.jpg' ;
    console.log(url_image) ;

    return (

        <Center py={6}>
            <Box
                maxW={'300'}
                minW={'300'}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit={'cover'}
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={url_image}
                        alt={'Author'}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={0} align={'center'} mb={5}>
                        <Tag colorScheme={"teal"} borderRadius={"full"}>{id} </Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>Age: {age}</Text>
                        <Text color={'gray.500'}>Gender: {gender}</Text>
                    </Stack>
                    <Stack>
                        <UpdateCustomerDrawer  id={id}  name={name}  email={email}  age={age}  gender={gender} fetchCustomers={fetchCustomers} />

                        <Button mt={3} colorScheme='red' onClick={onOpen}>Delete</Button>
                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Delete Customer
                                    </AlertDialogHeader>

                                    <AlertDialogBody>
                                        Are you sure? You can't undo this action afterwards.
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Cancel
                                        </Button>
                                        <Button colorScheme='red' onClick= {()=>{

                                            deleteCustomer(id).then(r => {
                                                notification("delete customer " + name , "has email " + email , "success")

                                            }).catch(err=>{
                                                notification( err.code,
                                                    err.response.data.message,
                                                    "error")
                                            }).finally(
                                                ()=>{
                                                    onClose()
                                                    fetchCustomers() ;
                                                }
                                            )
                                        }} ml={3}>
                                            Delete
                                        </Button>

                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>

                    </Stack>

                </Box>
            </Box>
        </Center>
    );
}