import { CloseButton, Dialog, IconButton, Portal } from "@chakra-ui/react";
import { FaEdit } from "react-icons/fa";
import { useState } from "react";
import type { Question } from "@/types";
import EditQuestionForm from "./EditQuestionForm";

const EditQuestionButton = ({ question }: { question: Question }) => {
	const [open, setOpen] = useState(false);

	const handleClose = () => setOpen(false);

	return (
		<Dialog.Root
			motionPreset="slide-in-bottom"
			open={open}
			onOpenChange={(e) => setOpen(e.open)}
		>
			<Dialog.Trigger asChild>
				<IconButton size="sm" aria-label="Edit question" variant="ghost">
					<FaEdit />
				</IconButton>
			</Dialog.Trigger>
			<Portal>
				<Dialog.Backdrop />
				<Dialog.Positioner>
					<Dialog.Content>
						<Dialog.Header>
							<Dialog.Title>Edit Question</Dialog.Title>
						</Dialog.Header>
						<Dialog.Body>
							<EditQuestionForm original={question} handleClose={handleClose} />
						</Dialog.Body>
						<Dialog.CloseTrigger asChild>
							<CloseButton size="sm" />
						</Dialog.CloseTrigger>
					</Dialog.Content>
				</Dialog.Positioner>
			</Portal>
		</Dialog.Root>
	);
};

export default EditQuestionButton;
