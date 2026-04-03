import { Container, Flex, Heading, IconButton, VStack } from "@chakra-ui/react";
import { LuGithub } from "react-icons/lu";
import { Link, Outlet } from "react-router";

function App() {
	return (
		<Container padding={4}>
			<Flex direction="column">
				<Flex
					padding={4}
					borderRadius="lg"
					bgColor="gray.100"
					justifyContent={"space-between"}
					marginBottom={4}
				>
					<Heading>Stacklite Dashboard</Heading>
					<IconButton aria-label="Call support" rounded="full" asChild>
						<a
							href="https://github.com/isyedaliraza/stacklite"
							rel="noopener noreferrer"
							target="_blank"
						>
							<LuGithub />
						</a>
					</IconButton>
				</Flex>
				<Flex flex={1} gap={4}>
					{/* Sidebar */}
					<VStack
						padding={4}
						borderRadius="lg"
						bgColor="gray.100"
						align="stretch"
					>
						<Link to="/">Questions</Link>
						<Link to="/answers">Answers</Link>
						<Link to="/tags">Tags</Link>
					</VStack>

					{/* Content */}
					<Outlet />
				</Flex>
			</Flex>
		</Container>
	);
}

export default App;
