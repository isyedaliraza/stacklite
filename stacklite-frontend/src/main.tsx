import { Provider } from "@/components/ui/provider.tsx";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import App from "./components/App.tsx";
import Home from "./components/Home.tsx";
import Questions from "./components/questions/Questions.tsx";
import Answers from "./components/Answers.tsx";
import Tags from "./components/Tags.tsx";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
	<StrictMode>
		<QueryClientProvider client={queryClient}>
			<Provider>
				<BrowserRouter>
					<Routes>
						<Route path="/" element={<App />}>
							<Route index element={<Questions />} />
							<Route path="answers" element={<Answers />} />
							<Route path="tags" element={<Tags />} />
						</Route>
					</Routes>
				</BrowserRouter>
			</Provider>
		</QueryClientProvider>
	</StrictMode>,
);
