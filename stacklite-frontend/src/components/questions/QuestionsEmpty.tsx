import { EmptyState, VStack } from "@chakra-ui/react";
import { FaList } from "react-icons/fa";

const QuestionsEmpty = () => {
	return (
		<EmptyState.Root>
			<EmptyState.Content>
				<EmptyState.Indicator>
					<FaList />
				</EmptyState.Indicator>
				<VStack textAlign="center">
					<EmptyState.Title>No Questions</EmptyState.Title>
					<EmptyState.Description>
						You don't have any questions. Add a new question.
					</EmptyState.Description>
				</VStack>
			</EmptyState.Content>
		</EmptyState.Root>
	);
};

export default QuestionsEmpty;
